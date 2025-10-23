package com.mitprojekt.ModelTests;

import org.junit.jupiter.api.Test;

import com.mitprojekt.model.Recipe;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeTest {
    
    @Test
    void testConstructorAndGetters() {
        // Arrange
        Recipe o = new Recipe(1, "Spaghetti Bolognese", "http://example.com", 30, "Kog pasta. Lav sauce.", true);

        // Assert
        assertEquals(1, o.getId());
        assertEquals("Spaghetti Bolognese", o.getName());
        assertEquals("http://example.com", o.getLinkToPage());
        assertEquals(30, o.getTime());
        assertEquals("Kog pasta. Lav sauce.", o.getProcedure());
        assertTrue(o.getHasMade());
    }

    @Test
    void testNameCannotBeEmpty() {
        // Assert
        assertThrows(IllegalArgumentException.class, () -> new Recipe(1, "", "http://example.com", 30, "Procedure", false));
    }

    @Test
    void testIdCannotBeNegative() {
        // Arrange
        Recipe o = new Recipe(1, "Pasta", "http://example.com", 20, "Procedure", false);
        
        // Assert
        assertThrows(IllegalArgumentException.class, () -> o.setId(-1));
    }

    @Test
    void testTimeCannotBeNegative() {
        // Arrange
        Recipe o = new Recipe(1, "Pasta", "http://example.com", 20, "Procedure", false);
        
        // Assert
        assertThrows(IllegalArgumentException.class, () -> o.setTime(-10));
    }

    @Test
    void testMatchesReturnsTrueForMatchingKeyword() {
        // Arrange
        Recipe o = new Recipe(1, "Hakket tomat pasta", "http://example.com", 25, "Procedure", false);
        
        // Assert
        assertTrue(o.matches("tomat"));
    }

    @Test
    void testMatchesReturnsFalseForNonMatch() {
        // Arrange
        Recipe o = new Recipe(1, "Hakket tomat pasta", "http://example.com", 25, "Procedure", false);
        
        // Assert
        assertFalse(o.matches("agurk"));
    }

    @Test
    void testToStringContainsName() {
        // Arrange
        Recipe o = new Recipe(3, "Ostebord", "http://example.com", 15, "Procedure", true);
        
        // Act
        String str = o.toString();
        
        // Assert
        assertTrue(str.contains("Ostebord"));
    }
}
