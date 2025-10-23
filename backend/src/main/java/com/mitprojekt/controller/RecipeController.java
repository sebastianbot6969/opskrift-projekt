package com.mitprojekt.controller;

import com.mitprojekt.dao.RecipeDAO;
import com.mitprojekt.model.Recipe;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeDAO recipeDAO = new RecipeDAO();

    @GetMapping
    public List<Recipe> getAllRecipes() {
        return recipeDAO.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable int id) {
        Recipe recipe = recipeDAO.getById(id);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }

    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
        Recipe createdRecipe = recipeDAO.insertRecipe(recipe);

        URI location = URI.create("/api/recipes/" + createdRecipe.getId());

        return ResponseEntity
                .created(location)
                .body(createdRecipe);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable int id, @RequestBody Recipe recipe) {
        recipe.setId(id);
        boolean updated = recipeDAO.updateRecipe(recipe);

        if (!updated) {
            return ResponseEntity.notFound().build();
        }

        Recipe updatedRecipe = recipeDAO.getById(id);
        return ResponseEntity.ok(updatedRecipe);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable int id) {
        boolean deleted = recipeDAO.deleteRecipe(id);

        if (!deleted) {
            return ResponseEntity.notFound().build(); 
        }
        
        return ResponseEntity.noContent().build(); 
    }
}
