package com.mitprojekt.service;

import com.mitprojekt.dao.RecipeIngredientDAO;
import com.mitprojekt.model.RecipeIngredient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeIngredientService {

    private final RecipeIngredientDAO recipeIngredientDAO;

    public RecipeIngredientService(RecipeIngredientDAO recipeIngredientDAO) {
        this.recipeIngredientDAO = recipeIngredientDAO;
    }

    /** CREATE */
    public RecipeIngredient addRecipeIngredient(RecipeIngredient recipeIngredient) {
        return recipeIngredientDAO.insertRecipeIngredient(recipeIngredient);
    }

    /** READ */
    public RecipeIngredient getRecipeIngredientById(int id) {
        return recipeIngredientDAO.getRecipeIngredientById(id);
    }

    /** READ ALL */
    public List<RecipeIngredient> getAllRecipeIngredients() {
        return recipeIngredientDAO.getAllRecipeIngredients();
    }

    /** UPDATE */
    public boolean updateRecipeIngredient(RecipeIngredient recipeIngredient) {
        return recipeIngredientDAO.updateRecipeIngredient(recipeIngredient);
    }

    /** DELETE */
    public boolean deleteRecipeIngredient(int id) {
        return recipeIngredientDAO.deleteRecipeIngredient(id);
    }
}
