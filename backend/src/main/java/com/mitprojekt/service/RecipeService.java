package com.mitprojekt.service;

import com.mitprojekt.dao.RecipeDAO;
import com.mitprojekt.dao.RecipeIngredientDAO;
import com.mitprojekt.model.Recipe;
import com.mitprojekt.model.RecipeIngredient;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

    private final RecipeDAO recipeDAO;
    private final RecipeIngredientDAO recipeIngredientDAO;

    public RecipeService(RecipeDAO recipeDAO, RecipeIngredientDAO recipeIngredientDAO) {
        this.recipeDAO = recipeDAO;
        this.recipeIngredientDAO = recipeIngredientDAO;
    }

    // Create
    public Recipe createRecipeWithIngredients(Recipe recipe) {
        // Først indsætter vi selve opskriften
        Recipe created = recipeDAO.insertRecipe(recipe);

        // Så indsætter vi ingredienserne, hvis der er nogen
        if (created != null && recipe.getIngredients() != null) {
            for (RecipeIngredient ri : recipe.getIngredients()) {
                ri.setRecipeId(created.getId()); // sæt recipe_id fra DB
                recipeIngredientDAO.insertRecipeIngredient(ri); // indsæt i recipe_ingredient-tabellen
            }
        }
        return created;
    }

    // Read all
    public List<Recipe> getAllRecipes() {
        return recipeDAO.getAll();
    }

    // Read one
    public Recipe getRecipeById(int id) {
        return recipeDAO.getById(id);
    }

    public List<Recipe> getRecipesByIngredient(int ingredientId) {
        return recipeDAO.getRecipesByIngredient(ingredientId);
    }

    // Update
    public Recipe updateRecipe(int id, Recipe updatedRecipe) {
        updatedRecipe.setId(id);
        boolean success = recipeDAO.updateRecipe(updatedRecipe);

        if (!success) {
            return null;
        }

        return recipeDAO.getById(id);
    }

    // Delete
    public boolean deleteRecipe(int id) {
        return recipeDAO.deleteRecipe(id);
    }
}
