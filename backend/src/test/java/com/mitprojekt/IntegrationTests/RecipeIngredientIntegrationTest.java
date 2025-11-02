package com.mitprojekt.IntegrationTests;

import com.mitprojekt.dao.IngredientDAO;
import com.mitprojekt.dao.RecipeDAO;
import com.mitprojekt.dao.RecipeIngredientDAO;
import com.mitprojekt.model.Ingredient;
import com.mitprojekt.model.Recipe;
import com.mitprojekt.model.RecipeIngredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeIngredientIntegrationTest {

    private RecipeDAO recipeDAO;
    private IngredientDAO ingredientDAO;
    private RecipeIngredientDAO recipeIngredientDAO;

    @BeforeEach
    void setUp() {
        recipeDAO = new RecipeDAO();
        ingredientDAO = new IngredientDAO();
        recipeIngredientDAO = new RecipeIngredientDAO();
    }

    @Test
    void testRecipeIngredientCRUD() {
        // Create Recipe and Ingredient
        Recipe r = new Recipe("Cake", "http://example.com", 45, "Bake", false);
        r = recipeDAO.insertRecipe(r);
        Ingredient i = new Ingredient("Sugar");
        i = ingredientDAO.insertIngredient(i);

        // Add RecipeIngredient
        RecipeIngredient rig = new RecipeIngredient(r.getId(), i.getId(), "200g");
        RecipeIngredient ri = recipeIngredientDAO.insertRecipeIngredient(rig);
        assertNotNull(ri);
        assertTrue(ri.getId() > 0);

        // Fetch all
        List<RecipeIngredient> allRIs = recipeIngredientDAO.getAllRecipeIngredients();
        assertTrue(allRIs.stream().anyMatch(x -> x.getId() == ri.getId()));

        // Update
        ri.setAmount("250g");
        boolean updated = recipeIngredientDAO.updateRecipeIngredient(ri);
        assertTrue(updated);
        RecipeIngredient updatedRI = recipeIngredientDAO.getRecipeIngredientById(ri.getId());
        assertEquals("250g", updatedRI.getAmount());

        // Delete
        boolean deleted = recipeIngredientDAO.deleteRecipeIngredient(ri.getId());
        assertTrue(deleted);
        RecipeIngredient deletedRI = recipeIngredientDAO.getRecipeIngredientById(ri.getId());
        assertNull(deletedRI);
    }
}
