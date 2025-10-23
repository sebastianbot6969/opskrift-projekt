package com.mitprojekt.ControllerTests;

import com.mitprojekt.model.Ingredient;
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
public class IngredientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateIngredient() throws Exception {
        Ingredient ingredient = new Ingredient("Tomato");

        mockMvc.perform(post("/api/ingredients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ingredient)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Tomato"));
    }

    @Test
    void testGetAllIngredients() throws Exception {
        mockMvc.perform(get("/api/ingredients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetIngredientById() throws Exception {
        Ingredient ingredient = new Ingredient("Cheese");
        String response = mockMvc.perform(post("/api/ingredients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ingredient)))
                .andReturn().getResponse().getContentAsString();

        Ingredient created = objectMapper.readValue(response, Ingredient.class);

        mockMvc.perform(get("/api/ingredients/" + created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Cheese"));
    }

    @Test
    void testUpdateIngredient() throws Exception {
        Ingredient ingredient = new Ingredient("Lettuce");
        String response = mockMvc.perform(post("/api/ingredients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ingredient)))
                .andReturn().getResponse().getContentAsString();

        Ingredient created = objectMapper.readValue(response, Ingredient.class);

        // Update name
        created.setName("Romaine Lettuce");

        mockMvc.perform(put("/api/ingredients/" + created.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(created)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Romaine Lettuce"));
    }

    @Test
    void testDeleteIngredient() throws Exception {
        Ingredient ingredient = new Ingredient("Onion");
        String response = mockMvc.perform(post("/api/ingredients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ingredient)))
                .andReturn().getResponse().getContentAsString();

        Ingredient created = objectMapper.readValue(response, Ingredient.class);

        mockMvc.perform(delete("/api/ingredients/" + created.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/ingredients/" + created.getId()))
                .andExpect(status().isNotFound());
    }
}
