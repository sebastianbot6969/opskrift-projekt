package com.mitprojekt;

import com.mitprojekt.dao.IngrediensDAO;

import com.mitprojekt.model.Ingrediens;
import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class IngrediensDAOTest {

    static IngrediensDAO dao;

    @BeforeAll
    static void setup() {
        dao = new IngrediensDAO();
    }

    @Test
    void testInsertIngrediens() {
        Ingrediens i = new Ingrediens("Tomat");
        Ingrediens result = dao.insertIngrediens(i);
        assertTrue(result.getId() > 0, "Efter insert skal id sættes af databasen");
    }

    @Test
    void testGetAllReturnsList() {
        List<Ingrediens> list = dao.getAll();
        assertNotNull(list, "Listen må ikke være null");
    }

    @Test
    void testInsertAndFetch() {
        Ingrediens i = new Ingrediens("Løg");
        dao.insertIngrediens(i);

        List<Ingrediens> all = dao.getAll();
        boolean found = all.stream().anyMatch(x -> x.getName().equals("Løg"));
        assertTrue(found, "Den indsatte ingrediens skal findes i databasen");
    }

    @Test
    void testDeleteIngrediens() {
        Ingrediens i = new Ingrediens("Hvidløg");
        i = dao.insertIngrediens(i);

        boolean deleted = dao.deleteIngrediens(i.getId());
        assertTrue(deleted, "Sletningen skal returnere true");

        List<Ingrediens> all = dao.getAll();
        int id = i.getId();
        boolean stillExists = all.stream().anyMatch(x -> x.getId() == id);
        assertFalse(stillExists, "Ingrediensen skal være slettet");
    }

    @Test
    void testUpdateIngrediens() {
        // 1️⃣ Insert først en ingrediens
        Ingrediens i = new Ingrediens("Peberfrugt");
        i = dao.insertIngrediens(i);

        // 2️⃣ Opdater navnet og parentId
        i.setName("Rød Peberfrugt");
        i.setParentId(0); // tip: nul eller positivt tal
        boolean updated = dao.updateIngrediens(i);

        assertTrue(updated, "Update skal returnere true hvis opdateringen lykkes");

        // 3️⃣ Hent ingrediensen igen og tjek værdier
        Ingrediens fetched = dao.getById(i.getId());
        assertEquals("Rød Peberfrugt", fetched.getName(), "Navnet skal være opdateret");
        assertEquals(0, fetched.getParentId(), "ParentId skal være opdateret");
    }

    @Test
    void testGetById() {
        Ingrediens i = new Ingrediens("Lime");
        i = dao.insertIngrediens(i);

        Ingrediens fetched = dao.getById(i.getId());
        assertNotNull(fetched, "Ingrediens skal findes via getById");
        assertEquals(i.getId(), fetched.getId(), "ID skal matche");
        assertEquals(i.getName(), fetched.getName(), "Navn skal matche");
    }

    @Test
    void testUpdateNonExisting() {
        // Prøv at opdatere en ingrediens, som ikke findes
        Ingrediens i = new Ingrediens(99999, "Fiktiv", 0);
        boolean updated = dao.updateIngrediens(i);
        assertFalse(updated, "Update skal returnere false hvis ingrediens ikke findes");
    }
}
