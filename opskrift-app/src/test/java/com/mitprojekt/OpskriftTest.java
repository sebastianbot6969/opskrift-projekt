package com.mitprojekt;

import org.junit.jupiter.api.Test;

import com.mitprojekt.model.Opskrift;

import static org.junit.jupiter.api.Assertions.*;

public class OpskriftTest {
    
    @Test
    void testConstructorAndGetters() {
        Opskrift o = new Opskrift(1, "Spaghetti Bolognese", "http://example.com", 30, "Kog pasta. Lav sauce.", true);
        assertEquals(1, o.getId());
        assertEquals("Spaghetti Bolognese", o.getName());
        assertEquals("http://example.com", o.getLinkToPage());
        assertEquals(30, o.getTime());
        assertEquals("Kog pasta. Lav sauce.", o.getProcedure());
        assertTrue(o.getHasMade());
    }

    @Test
    void testNameCannotBeEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new Opskrift(1, "", "http://example.com", 30, "Procedure", false));
    }

    @Test
    void testIdCannotBeNegative() {
        Opskrift o = new Opskrift(1, "Pasta", "http://example.com", 20, "Procedure", false);
        assertThrows(IllegalArgumentException.class, () -> o.setId(-1));
    }

    @Test
    void testTimeCannotBeNegative() {
        Opskrift o = new Opskrift(1, "Pasta", "http://example.com", 20, "Procedure", false);
        assertThrows(IllegalArgumentException.class, () -> o.setTime(-10));
    }

    @Test
    void testMatchesReturnsTrueForMatchingKeyword() {
        Opskrift o = new Opskrift(1, "Hakket tomat pasta", "http://example.com", 25, "Procedure", false);
        assertTrue(o.matches("tomat"));
    }

    @Test
    void testMatchesReturnsFalseForNonMatch() {
        Opskrift o = new Opskrift(1, "Hakket tomat pasta", "http://example.com", 25, "Procedure", false);
        assertFalse(o.matches("agurk"));
    }

    @Test
    void testToStringContainsName() {
        Opskrift o = new Opskrift(3, "Ostebord", "http://example.com", 15, "Procedure", true);
        assertTrue(o.toString().contains("Ostebord"));
    }
}
