package com.mitprojekt.model;


public class Ingrediens {
    private int id;
    private String navn;
    private int parentId; // Foreign key to Opskrift

    public Ingrediens(int id, String navn, int parentId) {
        this.id = id;
        this.navn = navn;
        this.parentId = parentId;
    }

    public Ingrediens(String navn) {
        this.navn = navn;
    }

    // Getters and setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNavn() {
        return navn;
    }
    public void setNavn(String navn) {
        this.navn = navn;
    }

    public int getParentId() {
        return parentId;
    }
    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "Ingrediens [id=" + id + ", navn=" + navn + ", parentId=" + parentId + "]";
    }
}
