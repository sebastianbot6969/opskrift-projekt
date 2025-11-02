package com.mitprojekt.model;

public class RecipeIngredient {
    private int id;
    private int RecipeId;
    private int IngredientId;
    private String amount;

    public RecipeIngredient() {
    }

    public RecipeIngredient(int id, int RecipeId, int IngredientId, String amount) {
        setId(id);
        setRecipeId(RecipeId);
        setIngredientId(IngredientId);
        setAmount(amount);
    }

    public RecipeIngredient(int RecipeId, int IngredientId, String amount) {
        setRecipeId(RecipeId);
        setIngredientId(IngredientId);
        setAmount(amount);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("Id cannot be negative");
        }
        this.id = id;
    }

    public int getRecipeId() {
        return RecipeId;
    }

    public void setRecipeId(int RecipeId) {
        if (RecipeId < 0) {
            throw new IllegalArgumentException("Recipe ID cannot be negative");
        }
        this.RecipeId = RecipeId;
    }

    public int getIngredientId() {
        return IngredientId;
    }

    public void setIngredientId(int IngredientId) {
        if (IngredientId < 0) {
            throw new IllegalArgumentException("Ingredient ID cannot be negative");
        }
        this.IngredientId = IngredientId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        if (amount == null || amount.trim().isEmpty()) {
            throw new IllegalArgumentException("amount cannot be null or empty");
        }
        this.amount = amount.trim();
    }

    @Override
    public String toString() {
        return String.format("RecipeIngredient{id=%d, RecipeId=%d, IngredientId=%d, amount='%s'}", id, RecipeId, IngredientId, amount);
    }
}
