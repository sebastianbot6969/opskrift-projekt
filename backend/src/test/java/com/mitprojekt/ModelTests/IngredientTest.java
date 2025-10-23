package com.mitprojekt.ModelTests;

import org.junit.jupiter.api.Test;

import com.mitprojekt.model.Ingredient;

import static org.junit.jupiter.api.Assertions.*;

public class IngredientTest {
    
    @Test
    void testConstructorAndGetters() {
        // Arrange
        Ingredient i = new Ingredient(1, "Tomat", 0);

        // Assert
        assertEquals(1, i.getId());
        assertEquals("Tomat", i.getName());
        assertEquals(0, i.getParentId());
    }

    @Test
    void testNameCannotBeEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new Ingredient(""));
    }

    @Test
    void testIdCannotBeNegative() {
        // Arrange
        Ingredient i = new Ingredient("Salt");
    
        // Assert
        assertThrows(IllegalArgumentException.class, () -> i.setId(-1));
    }

    @Test
    void testParentIdCannotBeNegative() {
        // Arrange
        Ingredient i = new Ingredient("Smør");

        // Assert
        assertThrows(IllegalArgumentException.class, () -> i.setParentId(-5));
    }

    @Test
    void testMatchesReturnsTrueForMatchingKeyword() {
        // Arrange
        Ingredient i = new Ingredient("Hakket tomat");
    
        // Assert
        assertTrue(i.matches("tomat"));
    }

    @Test
    void testMatchesReturnsFalseForNonMatch() {
        // Arrange
        Ingredient i = new Ingredient("Hakket tomat");

        // Assert
        assertFalse(i.matches("agurk"));
    }

    @Test
    void testToStringContainsName() {
        // Arrange
        Ingredient i = new Ingredient(3, "Ost", 0);

        // Assert
        assertTrue(i.toString().contains("Ost"));
    }

}
