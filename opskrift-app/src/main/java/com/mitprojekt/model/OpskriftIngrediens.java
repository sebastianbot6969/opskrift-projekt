package com.mitprojekt.model;

public class OpskriftIngrediens {
    private int id;
    private int opskriftId;
    private int ingrediensId;
    private String amount;

    public OpskriftIngrediens(int id, int opskriftId, int ingrediensId, String amount) {
        setId(id);
        setOpskriftId(opskriftId);
        setIngrediensId(ingrediensId);
        setAmount(amount);
    }

    public OpskriftIngrediens(int opskriftId, int ingrediensId, String amount) {
        setOpskriftId(opskriftId);
        setIngrediensId(ingrediensId);
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

    public int getOpskriftId() {
        return opskriftId;
    }

    public void setOpskriftId(int opskriftId) {
        if (opskriftId < 0) {
            throw new IllegalArgumentException("opskrift ID cannot be negative");
        }
        this.opskriftId = opskriftId;
    }

    public int getIngrediensId() {
        return ingrediensId;
    }

    public void setIngrediensId(int ingrediensId) {
        if (ingrediensId < 0) {
            throw new IllegalArgumentException("Ingrediens ID cannot be negative");
        }
        this.ingrediensId = ingrediensId;
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
        return String.format("OpskriftIngrediens{id=%d, opskriftId=%d, ingrediensId=%d, amount='%s'}", id, opskriftId, ingrediensId, amount);
    }
}
