package com.mitprojekt.IntegrationTests;

import com.mitprojekt.dao.IngredientDAO;
import com.mitprojekt.dao.RecipeDAO;
import com.mitprojekt.dao.RecipeIngredientDAO;
import com.mitprojekt.model.Ingredient;
import com.mitprojekt.model.Recipe;
import com.mitprojekt.model.RecipeIngredient;
import com.mitprojekt.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FullIntegrationTest {

    private RecipeDAO recipeDAO;
    private IngredientDAO ingredientDAO;
    private RecipeIngredientDAO recipeIngredientDAO;
    private RecipeService recipeService;

    @BeforeEach
    void setUp() {
        recipeDAO = new RecipeDAO();
        ingredientDAO = new IngredientDAO();
        recipeIngredientDAO = new RecipeIngredientDAO();
        recipeService = new RecipeService(recipeDAO, recipeIngredientDAO);
    }

    @Test
    void testRecipeCRUD() {
        // Create
        Recipe r = new Recipe("Pancakes", "http://example.com", 15, "Mix and fry", false);
        r = recipeDAO.insertRecipe(r);
        assertNotNull(r);
        assertTrue(r.getId() > 0);

        // Read
        Recipe fetched = recipeDAO.getById(r.getId());
        assertNotNull(fetched);
        assertEquals("Pancakes", fetched.getName());

        // Update
        r.setName("Blueberry Pancakes");
        boolean updated = recipeDAO.updateRecipe(r);
        assertTrue(updated);
        Recipe updatedRecipe = recipeDAO.getById(r.getId());
        assertEquals("Blueberry Pancakes", updatedRecipe.getName());

        // Delete
        boolean deleted = recipeDAO.deleteRecipe(r.getId());
        assertTrue(deleted);
        Recipe deletedRecipe = recipeDAO.getById(r.getId());
        assertNull(deletedRecipe);
    }

    @Test
    void testIngredientCRUD() {
        // Create
        Ingredient i = new Ingredient("Flour");
        i = ingredientDAO.insertIngredient(i);
        assertNotNull(i);
        assertTrue(i.getId() > 0);

        // Read
        Ingredient fetched = ingredientDAO.getById(i.getId());
        assertNotNull(fetched);
        assertEquals("Flour", fetched.getName());

        // Update
        i.setName("Whole Wheat Flour");
        boolean updated = ingredientDAO.updateIngredient(i);
        assertTrue(updated);
        Ingredient updatedIngredient = ingredientDAO.getById(i.getId());
        assertEquals("Whole Wheat Flour", updatedIngredient.getName());

        // Delete
        boolean deleted = ingredientDAO.deleteIngredient(i.getId());
        assertTrue(deleted);
        Ingredient deletedIngredient = ingredientDAO.getById(i.getId());
        assertNull(deletedIngredient);
    }

    @Test
    void testRecipeIngredientRelation() {
        // Create Ingredient & Recipe
        Ingredient i = new Ingredient("Sugar");
        i = ingredientDAO.insertIngredient(i);
        Recipe r = new Recipe("Cake", "http://example.com", 40, "Bake", false);
        r = recipeDAO.insertRecipe(r);

        // Add relation
        RecipeIngredient rig = new RecipeIngredient(r.getId(), i.getId(), "200g");
        RecipeIngredient ri = recipeIngredientDAO.insertRecipeIngredient(rig);
        assertNotNull(ri);
        assertTrue(ri.getId() > 0);

        // Fetch all relations
        List<RecipeIngredient> allRIs = recipeIngredientDAO.getAllRecipeIngredients();
        assertTrue(allRIs.stream().anyMatch(x -> x.getId() == ri.getId()));

        // Update relation
        ri.setAmount("250g");
        boolean updated = recipeIngredientDAO.updateRecipeIngredient(ri);
        assertTrue(updated);
        RecipeIngredient updatedRI = recipeIngredientDAO.getRecipeIngredientById(ri.getId());
        assertEquals("250g", updatedRI.getAmount());

        // Delete relation
        boolean deleted = recipeIngredientDAO.deleteRecipeIngredient(ri.getId());
        assertTrue(deleted);
        RecipeIngredient deletedRI = recipeIngredientDAO.getRecipeIngredientById(ri.getId());
        assertNull(deletedRI);
    }

    @Test
    void testGetRecipesByIngredient() {
        // Setup Ingredient
        Ingredient i = new Ingredient("Tomato");
        i = ingredientDAO.insertIngredient(i);

        // Setup Recipes
        Recipe r1 = new Recipe("Tomato Soup", "http://example.com", 15, "Boil tomatoes", false);
        Recipe r2 = new Recipe("Salad", "http://example.com", 10, "Chop tomatoes", false);
        r1 = recipeDAO.insertRecipe(r1);
        r2 = recipeDAO.insertRecipe(r2);

        // Link ingredients
        recipeIngredientDAO.insertRecipeIngredient(new RecipeIngredient(r1.getId(), i.getId(), "2 pcs"));
        recipeIngredientDAO.insertRecipeIngredient(new RecipeIngredient(r2.getId(), i.getId(), "3 pcs"));

        // Test service method
        List<Recipe> recipesWithTomato = recipeService.getRecipesByIngredient(i.getId());
        assertNotNull(recipesWithTomato);
        assertEquals(2, recipesWithTomato.size());
        assertTrue(recipesWithTomato.stream().anyMatch(r -> r.getName().equals("Tomato Soup")));
        assertTrue(recipesWithTomato.stream().anyMatch(r -> r.getName().equals("Salad")));
    }

    @Test
    void testEdgeCases() {
        // Get non-existent Recipe
        Recipe r = recipeDAO.getById(9999);
        assertNull(r);

        // Delete non-existent Ingredient
        boolean deleted = ingredientDAO.deleteIngredient(9999);
        assertFalse(deleted);

        // Fetch recipes for ingredient that does not exist
        List<Recipe> recipes = recipeService.getRecipesByIngredient(9999);
        assertNotNull(recipes);
        assertEquals(0, recipes.size());
    }
}
