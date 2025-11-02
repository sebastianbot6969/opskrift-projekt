package com.mitprojekt.IntegrationTests;

import com.mitprojekt.dao.IngredientDAO;
import com.mitprojekt.model.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IngredientIntegrationTest {

    private IngredientDAO ingredientDAO;

    @BeforeEach
    void setUp() {
        ingredientDAO = new IngredientDAO();
    }

    @Test
    void testIngredientCRUD() {
        // Create
        Ingredient ing = new Ingredient("Salt");
        ing = ingredientDAO.insertIngredient(ing);
        assertNotNull(ing);
        assertTrue(ing.getId() > 0);

        // Read
        Ingredient fetched = ingredientDAO.getById(ing.getId());
        assertNotNull(fetched);
        assertEquals("Salt", fetched.getName());

        // Update
        ing.setName("Sea Salt");
        boolean updated = ingredientDAO.updateIngredient(ing);
        assertTrue(updated);
        Ingredient updatedIng = ingredientDAO.getById(ing.getId());
        assertEquals("Sea Salt", updatedIng.getName());

        // Delete
        boolean deleted = ingredientDAO.deleteIngredient(ing.getId());
        assertTrue(deleted);
        Ingredient deletedIng = ingredientDAO.getById(ing.getId());
        assertNull(deletedIng);
    }
}
