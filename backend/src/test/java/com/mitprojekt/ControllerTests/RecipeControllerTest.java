package com.mitprojekt.ControllerTests;

import com.mitprojekt.model.Recipe;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = com.mitprojekt.App.class)
@AutoConfigureMockMvc
public class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateRecipe() throws Exception {
        Recipe recipe = new Recipe("Pasta Carbonara", "http://example.com", 20, "Cook pasta. Mix sauce.", false);

        mockMvc.perform(post("/api/recipes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recipe)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location")) 
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Pasta Carbonara"));
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
    void testUpdateRecipe() throws Exception {
        // Create a recipe
        Recipe recipe = new Recipe("Salad", "http://example.com", 10, "Mix veggies.", false);
        String response = mockMvc.perform(post("/api/recipes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recipe)))
                .andReturn().getResponse().getContentAsString();

        Recipe created = objectMapper.readValue(response, Recipe.class);

        // Update it
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
        // Create a recipe
        Recipe recipe = new Recipe("Soup", "http://example.com", 15, "Boil water.", false);
        String response = mockMvc.perform(post("/api/recipes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recipe)))
                .andReturn().getResponse().getContentAsString();

        Recipe created = objectMapper.readValue(response, Recipe.class);

        // Delete it
        mockMvc.perform(delete("/api/recipes/" + created.getId()))
                .andExpect(status().isNoContent()); 

        // Verify deletion
        mockMvc.perform(get("/api/recipes/" + created.getId()))
                .andExpect(status().isNotFound());
    }

}
