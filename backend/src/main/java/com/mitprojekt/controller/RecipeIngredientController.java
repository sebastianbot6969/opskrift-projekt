package com.mitprojekt.controller;

import com.mitprojekt.model.RecipeIngredient;
import com.mitprojekt.service.RecipeIngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipe-ingredients")
public class RecipeIngredientController {

    private final RecipeIngredientService recipeIngredientService;

    public RecipeIngredientController(RecipeIngredientService recipeIngredientService) {
        this.recipeIngredientService = recipeIngredientService;
    }

    /** CREATE */
    @PostMapping
    public ResponseEntity<RecipeIngredient> create(@RequestBody RecipeIngredient recipeIngredient) {
        RecipeIngredient created = recipeIngredientService.addRecipeIngredient(recipeIngredient);
        return ResponseEntity.status(201).body(created); // 201 Created
    }

    /** READ ALL */
    @GetMapping
    public List<RecipeIngredient> getAll() {
        return recipeIngredientService.getAllRecipeIngredients();
    }

    /** READ BY ID */
    @GetMapping("/{id}")
    public ResponseEntity<RecipeIngredient> getById(@PathVariable int id) {
        RecipeIngredient ri = recipeIngredientService.getRecipeIngredientById(id);
        if (ri == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ri);
    }

    /** UPDATE */
    @PutMapping("/{id}")
    public ResponseEntity<RecipeIngredient> update(@PathVariable int id, @RequestBody RecipeIngredient recipeIngredient) {
        recipeIngredient.setId(id);
        boolean updated = recipeIngredientService.updateRecipeIngredient(recipeIngredient);
        if (!updated) {
            return ResponseEntity.notFound().build();
        }
        RecipeIngredient updatedRi = recipeIngredientService.getRecipeIngredientById(id);
        return ResponseEntity.ok(updatedRi);
    }

    /** DELETE */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        boolean deleted = recipeIngredientService.deleteRecipeIngredient(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
