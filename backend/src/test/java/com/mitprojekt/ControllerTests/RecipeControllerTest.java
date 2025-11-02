package com.mitprojekt.ControllerTests;

import com.mitprojekt.dao.IngredientDAO;
import com.mitprojekt.dao.RecipeDAO;
import com.mitprojekt.model.Ingredient;
import com.mitprojekt.model.Recipe;
import com.mitprojekt.model.RecipeIngredient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@SpringBootTest(classes = com.mitprojekt.App.class)
@AutoConfigureMockMvc
public class RecipeControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private RecipeDAO recipeDAO;

        @Autowired
        private IngredientDAO ingredientDAO;

        @Test
        void testCreateRecipeWithIngredients() throws Exception {
                RecipeIngredient ri1 = new RecipeIngredient(0, 1, "200g");
                RecipeIngredient ri2 = new RecipeIngredient(0, 2, "100g");

                Recipe recipe = new Recipe("Carbonara", "http://example.com", 20, "Cook pasta.", false);
                recipe.setIngredients(List.of(ri1, ri2));

                String response = mockMvc.perform(post("/api/recipes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(recipe)))
                                .andExpect(status().isCreated())
                                .andReturn().getResponse().getContentAsString();

                Recipe created = objectMapper.readValue(response, Recipe.class);
                assertNotNull(created.getId());
                assertEquals(2, created.getIngredients().size());
        }

        @Test
        void testGetAllRecipes() throws Exception {
                mockMvc.perform(get("/api/recipes"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$").isArray());
        }

        @Test
        void testGetRecipeById() throws Exception {
                Recipe recipe = new Recipe("Pizza Margherita", "http://example.com", 25, "Bake pizza.", false);

                String response = mockMvc.perform(post("/api/recipes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(recipe)))
                                .andReturn().getResponse().getContentAsString();

                Recipe created = objectMapper.readValue(response, Recipe.class);

                mockMvc.perform(get("/api/recipes/" + created.getId()))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.name").value("Pizza Margherita"));
        }

        @Test
        void testGetRecipesByIngredient() throws Exception {
                // Opret ingrediens
                Ingredient ing = new Ingredient("Cheese");
                ing = ingredientDAO.insertIngredient(ing);

                // Opret opskrift
                Recipe recipe = new Recipe("Cheese Pizza", "http://example.com", 20, "Bake pizza", false);
                recipe = recipeDAO.insertRecipe(recipe);

                // Link ingrediens til opskrift
                recipeDAO.addIngredientToRecipe(recipe.getId(), ing);

                // Test endpoint
                mockMvc.perform(get("/api/recipes/by-ingredient/" + ing.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].name").value("Cheese Pizza"));
        }

        @Test
        void testUpdateRecipe() throws Exception {
                Recipe recipe = new Recipe("Salad", "http://example.com", 10, "Mix veggies.", false);

                String response = mockMvc.perform(post("/api/recipes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(recipe)))
                                .andReturn().getResponse().getContentAsString();

                Recipe created = objectMapper.readValue(response, Recipe.class);

                // update
                created.setName("Updated Salad");
                created.setHasMade(true);

                mockMvc.perform(put("/api/recipes/" + created.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(created)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.name").value("Updated Salad"));
        }

        @Test
        void testDeleteRecipe() throws Exception {
                Recipe recipe = new Recipe("Soup", "http://example.com", 15, "Boil water.", false);
                String response = mockMvc.perform(post("/api/recipes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(recipe)))
                                .andReturn().getResponse().getContentAsString();

                Recipe created = objectMapper.readValue(response, Recipe.class);

                mockMvc.perform(delete("/api/recipes/" + created.getId()))
                                .andExpect(status().isNoContent());

                mockMvc.perform(get("/api/recipes/" + created.getId()))
                                .andExpect(status().isNotFound());
        }
}
