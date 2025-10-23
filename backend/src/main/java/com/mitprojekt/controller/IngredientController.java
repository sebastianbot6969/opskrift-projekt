package com.mitprojekt.controller;

import com.mitprojekt.dao.IngredientDAO;
import com.mitprojekt.model.Ingredient;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
public class IngredientController {
    
    private final IngredientDAO ingredientDAO = new IngredientDAO();

    @GetMapping
    public List<Ingredient> getAllIngredients() {
        return ingredientDAO.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredient(@PathVariable int id) {
        Ingredient ingredient = ingredientDAO.getById(id);
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient);
    }

    @PostMapping
    public ResponseEntity<Ingredient> createIngredient(@RequestBody Ingredient ingredient) {
        Ingredient createdIngredient = ingredientDAO.insertIngredient(ingredient);

        URI location = URI.create("/api/ingredients/" + createdIngredient.getId());

        return ResponseEntity
                .created(location)
                .body(createdIngredient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> updateIngredient(@PathVariable int id, @RequestBody Ingredient ingredient) {
        ingredient.setId(id);
        boolean updated = ingredientDAO.updateIngredient(ingredient);
        if (!updated) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable int id) {
        boolean deleted = ingredientDAO.deleteIngredient(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
    
}
