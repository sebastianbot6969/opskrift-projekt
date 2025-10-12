package com.mitprojekt;

import org.junit.jupiter.api.Test;

import com.mitprojekt.model.Ingrediens;

import static org.junit.jupiter.api.Assertions.*;

public class IngrediensTest {
    
    @Test
    void testConstructorAndGetters() {
        Ingrediens i = new Ingrediens(1, "Tomat", 0);
        assertEquals(1, i.getId());
        assertEquals("Tomat", i.getName());
        assertEquals(0, i.getParentId());
    }

    @Test
    void testNameCannotBeEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new Ingrediens(""));
    }

    @Test
    void testIdCannotBeNegative() {
        Ingrediens i = new Ingrediens("Salt");
        assertThrows(IllegalArgumentException.class, () -> i.setId(-1));
    }

    @Test
    void testParentIdCannotBeNegative() {
        Ingrediens i = new Ingrediens("Smør");
        assertThrows(IllegalArgumentException.class, () -> i.setParentId(-5));
    }

    @Test
    void testMatchesReturnsTrueForMatchingKeyword() {
        Ingrediens i = new Ingrediens("Hakket tomat");
        assertTrue(i.matches("tomat"));
    }

    @Test
    void testMatchesReturnsFalseForNonMatch() {
        Ingrediens i = new Ingrediens("Hakket tomat");
        assertFalse(i.matches("agurk"));
    }

    @Test
    void testToStringContainsName() {
        Ingrediens i = new Ingrediens(3, "Ost", 0);
        assertTrue(i.toString().contains("Ost"));
    }

}
