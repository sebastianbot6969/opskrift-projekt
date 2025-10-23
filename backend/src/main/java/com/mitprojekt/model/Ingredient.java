package com.mitprojekt.model;


public class Ingredient {
    private int id;
    private String name;
    private int parentId; // Foreign key to Recipe

    public Ingredient(int id, String name, int parentId) {
        setId(id);
        setName(name);
        setParentId(parentId);
    }

    public Ingredient(String name) {
        setName(name);
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

    public boolean matches(String keyword) {
        return keyword != null && name.toLowerCase().contains(keyword.toLowerCase());
    }

    @Override
    public String toString() {
        return String.format("Ingredient{id=%d, name='%s', parentId=%d}", id, name, parentId);
    }
}