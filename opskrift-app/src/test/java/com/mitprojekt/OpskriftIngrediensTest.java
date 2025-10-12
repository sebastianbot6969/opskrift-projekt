package com.mitprojekt;

import org.junit.jupiter.api.Test;

import com.mitprojekt.model.OpskriftIngrediens;

import static org.junit.jupiter.api.Assertions.*;

public class OpskriftIngrediensTest {

    @Test
    void testConstructorAndGetters() {
        OpskriftIngrediens oi = new OpskriftIngrediens(1, 2, "200 g");
        assertEquals(1, oi.getOpskriftId());
        assertEquals(2, oi.getIngrediensId());
        assertEquals("200 g", oi.getAmount());
    }

    @Test
    void testsetters() {
        OpskriftIngrediens oi = new OpskriftIngrediens(0, 0, "1");

        oi.setOpskriftId(3);
        oi.setIngrediensId(4);
        oi.setAmount("150 ml");

        assertEquals(3, oi.getOpskriftId());
        assertEquals(4, oi.getIngrediensId());
        assertEquals("150 ml", oi.getAmount());
    }

    @Test
    void testToString () {
        OpskriftIngrediens oi = new OpskriftIngrediens(1, 2, "200 g");
        String str = oi.toString();
        assertTrue(str.contains("1"));
        assertTrue(str.contains("2"));
        assertTrue(str.contains("200 g"));
    }

    @Test
    void testIllegalValues() {
        OpskriftIngrediens oi = new OpskriftIngrediens(1, 2, "200 g");

        assertThrows(IllegalArgumentException.class, () -> oi.setId(-1));
        assertThrows(IllegalArgumentException.class, () -> oi.setOpskriftId(-5));
        assertThrows(IllegalArgumentException.class, () -> oi.setIngrediensId(-10));
        assertThrows(IllegalArgumentException.class, () -> oi.setAmount(""));
        assertThrows(IllegalArgumentException.class, () -> oi.setAmount(null));
    }

//     @Test
//     void testNameCannotBeEmpty() {
//         assertThrows(IllegalArgumentException.class, () -> new OpskriftIngrediens(1, "", 100, 2));
//     }

//     @Test
//     void testIdCannotBeNegative() {
//         OpskriftIngrediens oi = new OpskriftIngrediens(1, "Pasta", 100, 2);
//         assertThrows(IllegalArgumentException.class, () -> oi.setId(-1));
//     }

//     @Test
//     void testMængdeCannotBeNegative() {
//         OpskriftIngrediens oi = new OpskriftIngrediens(1, "Pasta", 100, 2);
//         assertThrows(IllegalArgumentException.class, () -> oi.setMængde(-10));
//     }

//     @Test
//     void testToStringContainsName() {
//         OpskriftIngrediens oi = new OpskriftIngrediens(1, "Pasta", 100, 2);
//         assertTrue(oi.toString().contains("Pasta"));
//     }
// }

//     @Test
//     void testConstructorAndGetters() {
//         Opskrift o = new Opskrift(1, "Spaghetti Bolognese", "http://example.com", 30, "Kog pasta. Lav sauce.", true);
//         assertEquals(1, o.getId());
//         assertEquals("Spaghetti Bolognese", o.getName());
//         assertEquals("http://example.com", o.getLinkToPage());
//         assertEquals(30, o.getTime());
//         assertEquals("Kog pasta. Lav sauce.", o.getProcedure());
//         assertTrue(o.getHasMade());
//     }

//     @Test
//     void testNameCannotBeEmpty() {
//         assertThrows(IllegalArgumentException.class, () -> new Opskrift(1, "", "http://example.com", 30, "Procedure", false));
//     }

//     @Test
//     void testIdCannotBeNegative() {
//         Opskrift o = new Opskrift(1, "Pasta", "http://example.com", 20, "Procedure", false);
//         assertThrows(IllegalArgumentException.class, () -> o.setId(-1));
//     }

//     @Test
//     void testTimeCannotBeNegative() {
//         Opskrift o = new Opskrift(1, "Pasta", "http://example.com", 20, "Procedure", false);
//         assertThrows(IllegalArgumentException.class, () -> o.setTime(-10));
//     }

//     @Test
//     void testMatchesReturnsTrueForMatchingKeyword() {
//         Opskrift o = new Opskrift(1, "Hakket tomat pasta", "http://example.com", 25, "Procedure", false);
//         assertTrue(o.matches("tomat"));
//     }

//     @Test
//     void testMatchesReturnsFalseForNonMatch() {
//         Opskrift o = new Opskrift(1, "Hakket tomat pasta", "http://example.com", 25, "Procedure", false);
//         assertFalse(o.matches("agurk"));
//     }

//     @Test
//     void testToStringContainsName() {
//         Opskrift o = new Opskrift(3, "Ostebord", "http://example.com", 15, "Procedure", true);
//         assertTrue(o.toString().contains("Ostebord"));
//     }
}
