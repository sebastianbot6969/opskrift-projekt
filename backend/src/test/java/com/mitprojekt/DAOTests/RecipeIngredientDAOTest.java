package com.mitprojekt.DAOTests;

import com.mitprojekt.dao.RecipeIngredientDAO;
import com.mitprojekt.model.RecipeIngredient;

import java.util.List;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class RecipeIngredientDAOTest {

    private RecipeIngredientDAO recipeIngredientDAO;

    @BeforeEach
    public void setUp() {
        recipeIngredientDAO = new RecipeIngredientDAO();
    }

    @Test
    public void testInsertRecipeIngredient() {
        //Arrange
        RecipeIngredient recipeIngredient = new RecipeIngredient(1, 1, "2 cups");

        //Act
        RecipeIngredient inserted = recipeIngredientDAO.insertRecipeIngredient(recipeIngredient);

        //Assert
        assertNotNull(inserted);
        assertTrue(inserted.getId() > 0);
        assertEquals("2 cups", inserted.getAmount());
    }

    @Test
    public void testGetRecipeIngredientById() {
        // Insert
        RecipeIngredient inserted = recipeIngredientDAO.insertRecipeIngredient(new RecipeIngredient(1, 1, "2 cups"));

        // Act
        RecipeIngredient fetched = recipeIngredientDAO.getRecipeIngredientById(inserted.getId());
        
        // Assert
        assertNotNull(fetched);
        assertEquals(inserted.getId(), fetched.getId());
        assertEquals("2 cups", fetched.getAmount());
    }

    @Test
    public void testUpdateRecipeIngredient() {
        // Arrange
        RecipeIngredient inserted = recipeIngredientDAO.insertRecipeIngredient(new RecipeIngredient(1, 1, "1 cup"));
        inserted.setAmount("3 cups");

        // Act
        boolean updated = recipeIngredientDAO.updateRecipeIngredient(inserted);
        RecipeIngredient fetched = recipeIngredientDAO.getRecipeIngredientById(inserted.getId());
        
        // Assert
        assertTrue(updated);
        assertEquals("3 cups", fetched.getAmount());
    }

    @Test
    public void testDeleteRecipeIngredient() {
        // Arrange 
        RecipeIngredient inserted = recipeIngredientDAO.insertRecipeIngredient(new RecipeIngredient(1, 1, "2 tbsp"));

        // Act
        boolean deleted = recipeIngredientDAO.deleteRecipeIngredient(inserted.getId());
        RecipeIngredient fetched = recipeIngredientDAO.getRecipeIngredientById(inserted.getId());
        
        // Assert 
        assertTrue(deleted);
        assertNull(fetched);
    }

    @Test
    public void testGetAllRecipeIngredients() {
        // Arrange
        recipeIngredientDAO.insertRecipeIngredient(new RecipeIngredient(1, 1, "1 cup"));
        recipeIngredientDAO.insertRecipeIngredient(new RecipeIngredient(1, 2, "2 tsp"));

        // Act
        List<RecipeIngredient> ingredients = recipeIngredientDAO.getAllRecipeIngredients();

        // Assert
        assertNotNull(ingredients);
        assertFalse(ingredients.isEmpty());
    }
}
