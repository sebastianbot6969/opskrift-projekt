package com.mitprojekt.IntegrationTests;

import com.mitprojekt.dao.IngredientDAO;
import com.mitprojekt.dao.RecipeDAO;
import com.mitprojekt.model.Ingredient;
import com.mitprojekt.model.Recipe;
import com.mitprojekt.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeIntegrationTest {

    private RecipeDAO recipeDAO;
    private IngredientDAO ingredientDAO;
    private RecipeService recipeService;

    @BeforeEach
    void setUp() {
        recipeDAO = new RecipeDAO();
        ingredientDAO = new IngredientDAO();
        recipeService = new RecipeService(recipeDAO, null); // RecipeIngredientDAO kan tilføjes senere
    }

    @Test
    void testInsertRecipeWithIngredients() {
        // 1️⃣ Opret ingredienser
        Ingredient tomato = ingredientDAO.insertIngredient(new Ingredient("Tomato"));
        Ingredient cheese = ingredientDAO.insertIngredient(new Ingredient("Cheese"));

        assertNotNull(tomato);
        assertTrue(tomato.getId() > 0);
        assertNotNull(cheese);
        assertTrue(cheese.getId() > 0);

        // 2️⃣ Opret opskrift
        Recipe pizza = new Recipe("Pizza Margherita", "http://example.com", 20, "Bake it", false);
        Recipe pizza1 = recipeDAO.insertRecipe(pizza);

        assertNotNull(pizza1);
        assertTrue(pizza1.getId() > 0);

        // 3️⃣ Tilføj ingredienser til opskriften
        recipeDAO.addIngredientToRecipe(pizza1.getId(), tomato);
        recipeDAO.addIngredientToRecipe(pizza1.getId(), cheese);

        // 4️⃣ Hent opskriften og tjek
        Recipe fetched = recipeDAO.getById(pizza1.getId());
        assertNotNull(fetched);
        assertEquals("Pizza Margherita", fetched.getName());

        List<Recipe> recipesWithTomato = recipeService.getRecipesByIngredient(tomato.getId());
        assertTrue(recipesWithTomato.stream().anyMatch(r -> r.getId() == pizza1.getId()));
    }
}
