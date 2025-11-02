package com.mitprojekt.ControllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mitprojekt.model.RecipeIngredient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = com.mitprojekt.App.class)
@AutoConfigureMockMvc
public class RecipeIngredientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateRecipeIngredient() throws Exception {
        RecipeIngredient ri = new RecipeIngredient( 1, 1, "200g");

        mockMvc.perform(post("/api/recipe-ingredients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ri)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.amount").value("200g"));
    }

    @Test
    void testGetAllRecipeIngredients() throws Exception {
        mockMvc.perform(get("/api/recipe-ingredients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetRecipeIngredientById() throws Exception {
        RecipeIngredient ri = new RecipeIngredient( 1, 1, "50g");

        String response = mockMvc.perform(post("/api/recipe-ingredients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ri)))
                .andReturn().getResponse().getContentAsString();

        RecipeIngredient created = objectMapper.readValue(response, RecipeIngredient.class);

        mockMvc.perform(get("/api/recipe-ingredients/" + created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value("50g"));
    }

    @Test
    void testUpdateRecipeIngredient() throws Exception {
        RecipeIngredient ri = new RecipeIngredient( 1, 1, "100g");

        String response = mockMvc.perform(post("/api/recipe-ingredients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ri)))
                .andReturn().getResponse().getContentAsString();

        RecipeIngredient created = objectMapper.readValue(response, RecipeIngredient.class);

        created.setAmount("150g");

        mockMvc.perform(put("/api/recipe-ingredients/" + created.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(created)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value("150g"));
    }

    @Test
    void testDeleteRecipeIngredient() throws Exception {
        RecipeIngredient ri = new RecipeIngredient( 1, 1, "75g");

        String response = mockMvc.perform(post("/api/recipe-ingredients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ri)))
                .andReturn().getResponse().getContentAsString();

        RecipeIngredient created = objectMapper.readValue(response, RecipeIngredient.class);

        mockMvc.perform(delete("/api/recipe-ingredients/" + created.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/recipe-ingredients/" + created.getId()))
                .andExpect(status().isNotFound());
    }
}
