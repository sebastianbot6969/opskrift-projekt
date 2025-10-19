package com.mitprojekt.model;

/**
 * * Represents an ingredient belonging to a recipe. * Each ingredient has an
 * ID, a name, and a reference (parentId) to its recipe.
 */
public class Ingredient {
    private int id;
    private String name;
    private int parentId; // Foreign key to Recipe

    /**
     * Full constructor used when all fields are known (e.g. when reading from DB).
     */
    public Ingredient(int id, String name, int parentId) {
        setId(id);
        setName(name);
        setParentId(parentId);
    }

    /**
     * Convenience constructor for creating a new ingredient before assigning IDs.
     */
    public Ingredient(String name) {
        setName(name);
    }

    // Getters and setters with basic validation
    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("Id cannot be negative");
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name.trim();
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        if (parentId < 0) {
            throw new IllegalArgumentException("ParentId cannot be negative");
        }
        this.parentId = parentId;
    }

    /** * Utility method to check if this ingredient matches a search keyword. */
    public boolean matches(String keyword) {
        return keyword != null && name.toLowerCase().contains(keyword.toLowerCase());
    }

    @Override
    public String toString() {
        return String.format("Ingredient{id=%d, name='%s', parentId=%d}", id, name, parentId);
    }
}