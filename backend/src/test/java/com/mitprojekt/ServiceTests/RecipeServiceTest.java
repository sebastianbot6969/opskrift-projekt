package com.mitprojekt.ServiceTests;

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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RecipeServiceTest {

    private RecipeDAO recipeDAO;
    private RecipeIngredientDAO recipeIngredientDAO;
    private RecipeService recipeService;
    private IngredientDAO ingredientDAO;

    @BeforeEach
    void setUp() {
        recipeDAO = mock(RecipeDAO.class);
        recipeIngredientDAO = mock(RecipeIngredientDAO.class);
        recipeService = new RecipeService(recipeDAO, recipeIngredientDAO);
        ingredientDAO = new IngredientDAO();
    }

    @Test
    void testCreateRecipeWithIngredients() {
        // Arrange
        RecipeIngredient ri1 = new RecipeIngredient(0, 1, "200g");
        RecipeIngredient ri2 = new RecipeIngredient(0, 2, "100g");

        Recipe recipe = new Recipe("Lasagna", "http://example.com", 60, "Bake it.", false);
        recipe.setIngredients(List.of(ri1, ri2));

        Recipe savedRecipe = new Recipe(1, "Lasagna", "http://example.com", 60, "Bake it.", false);
        savedRecipe.setIngredients(List.of(ri1, ri2));

        when(recipeDAO.insertRecipe(recipe)).thenReturn(savedRecipe);

        // Act
        Recipe result = recipeService.createRecipeWithIngredients(recipe);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(2, result.getIngredients().size());
        verify(recipeDAO, times(1)).insertRecipe(recipe);
        verify(recipeIngredientDAO, times(2)).insertRecipeIngredient(any(RecipeIngredient.class));
    }

    @Test
    void testGetAllRecipes() {
        when(recipeDAO.getAll()).thenReturn(List.of(
                new Recipe("Pizza", "link", 20, "Bake", false),
                new Recipe("Soup", "link", 10, "Boil", false)));

        List<Recipe> result = recipeService.getAllRecipes();

        assertEquals(2, result.size());
        verify(recipeDAO, times(1)).getAll();
    }

    @Test
    void testGetRecipeById() {
        Recipe recipe = new Recipe("Cake", "link", 45, "Mix", false);
        when(recipeDAO.getById(3)).thenReturn(recipe);

        Recipe result = recipeService.getRecipeById(3);

        assertEquals("Cake", result.getName());
        verify(recipeDAO, times(1)).getById(3);
    }

    @Test
    void testUpdateRecipeSuccess() {
        Recipe updated = new Recipe("Updated", "link", 30, "Cook", true);
        when(recipeDAO.updateRecipe(updated)).thenReturn(true);
        when(recipeDAO.getById(5)).thenReturn(updated);

        Recipe result = recipeService.updateRecipe(5, updated);

        assertNotNull(result);
        assertEquals("Updated", result.getName());
        verify(recipeDAO, times(1)).updateRecipe(updated);
    }

    @Test
    void testUpdateRecipeNotFound() {
        Recipe updated = new Recipe("Not found", "link", 10, "Mix", false);
        when(recipeDAO.updateRecipe(updated)).thenReturn(false);

        Recipe result = recipeService.updateRecipe(6, updated);

        assertNull(result);
    }

    @Test
    void testDeleteRecipe() {
        when(recipeDAO.deleteRecipe(4)).thenReturn(true);

        boolean result = recipeService.deleteRecipe(4);

        assertTrue(result);
        verify(recipeDAO, times(1)).deleteRecipe(4);
    }

    @Test
    void testinsertRecipeIngredient() {
        Ingredient ing = new Ingredient("Tomato");
        ing = ingredientDAO.insertIngredient(ing);

        // print ingredient id
        System.out.println("Inserted Ingredient ID: " + ing.getId());
        assertNotNull(ing, "Ingredient should not be null after insert");
    }
}
