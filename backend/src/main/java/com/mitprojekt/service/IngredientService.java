package com.mitprojekt.service;

import com.mitprojekt.dao.IngredientDAO;
import com.mitprojekt.model.Ingredient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    private final IngredientDAO ingredientDAO;

    public IngredientService(IngredientDAO ingredientDAO) {
        this.ingredientDAO = ingredientDAO;
    }

    // Create
    public Ingredient createIngredient(Ingredient ingredient) {
        if (ingredient == null || ingredient.getName() == null || ingredient.getName().isBlank()) {
            throw new IllegalArgumentException("Ingredient name cannot be null or blank");
        }

        return ingredientDAO.insertIngredient(ingredient);
    }

    // Read all
    public List<Ingredient> getAllIngredients() {
        return ingredientDAO.getAll();
    }

    // Read one
    public Ingredient getIngredientById(int id) {
        return ingredientDAO.getById(id);
    }

    // Update
    public Ingredient updateIngredient(int id, Ingredient updatedIngredient) {
        updatedIngredient.setId(id);
        boolean success = ingredientDAO.updateIngredient(updatedIngredient);

        if (!success) {
            return null;
        }

        return ingredientDAO.getById(id);
    }

    // Delete
    public boolean deleteIngredient(int id) {
        return ingredientDAO.deleteIngredient(id);
    }
}