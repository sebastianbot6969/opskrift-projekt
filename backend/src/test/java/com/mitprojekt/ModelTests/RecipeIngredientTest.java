package com.mitprojekt.ModelTests;

import org.junit.jupiter.api.Test;

import com.mitprojekt.model.RecipeIngredient;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeIngredientTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange
        RecipeIngredient oi = new RecipeIngredient(1, 2, "200 g");

        // Assert
        assertEquals(1, oi.getRecipeId());
        assertEquals(2, oi.getIngredientId());
        assertEquals("200 g", oi.getAmount());
    }

    @Test
    void testsetters() {
        // Arrange
        RecipeIngredient oi = new RecipeIngredient(0, 0, "1");

        // Act
        oi.setRecipeId(3);
        oi.setIngredientId(4);
        oi.setAmount("150 ml");

        // Assert
        assertEquals(3, oi.getRecipeId());
        assertEquals(4, oi.getIngredientId());
        assertEquals("150 ml", oi.getAmount());
    }

    @Test
    void testToString () {
        // Arrange
        RecipeIngredient oi = new RecipeIngredient(1, 2, "200 g");

        // Act
        String str = oi.toString();

        // Assert
        assertTrue(str.contains("1"));
        assertTrue(str.contains("2"));
        assertTrue(str.contains("200 g"));
    }

    @Test
    void testIllegalValues() {
        // Arrange
        RecipeIngredient oi = new RecipeIngredient(1, 2, "200 g");

        // Assert
        assertThrows(IllegalArgumentException.class, () -> oi.setId(-1));
        assertThrows(IllegalArgumentException.class, () -> oi.setRecipeId(-5));
        assertThrows(IllegalArgumentException.class, () -> oi.setIngredientId(-10));
        assertThrows(IllegalArgumentException.class, () -> oi.setAmount(""));
        assertThrows(IllegalArgumentException.class, () -> oi.setAmount(null));
    }
}
