package com.mitprojekt;

import com.mitprojekt.dao.OpskriftDAO;

import com.mitprojekt.model.Opskrift;
import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import com.mitprojekt.dao.SetupDatabase;

public class OpskriftDAOTest {

    static OpskriftDAO dao;

    @BeforeAll
    static void setup() {
        SetupDatabase.initialize();
        dao = new OpskriftDAO();
    }

    @Test
    void testInsertOpskrift() {
        //Arrange
        Opskrift o = new Opskrift("Spaghetti Bolognese", "http://example.com", 30, "Kog pasta. Lav sauce.", true);

        //Act 
        Opskrift result = dao.insertOpskrift(o);

        //Assert
        assertTrue(result.getId() > 0, "Efter insert skal id sættes af databasen");
    }

    @Test
    void testGetAllReturnsList() {
        //Act
        List<Opskrift> list = dao.getAll();

        //Assert
        assertNotNull(list, "Listen må ikke være null");
    }

    @Test
    void testInsertAndFetch() {
        //Arrange
        Opskrift o = new Opskrift("Pancakes", "http://example.com/pancakes", 20, "Bland ingredienser. Steg.", false);

        //Act
        Opskrift inserted = dao.insertOpskrift(o);
        List<Opskrift> all = dao.getAll();
        boolean found = all.stream().anyMatch(x -> x.getId() == inserted.getId());

        //Assert
        assertNotNull(inserted, "Insert skal returnere opskriften med id");
        assertTrue(inserted.getId() > 0, "Id skal være sat efter insert");
        assertTrue(found, "Den indsatte opskrift skal kunne findes i getAll");
    }

    @Test
    void testDeleteOpskrift() {
        //Arrange
        Opskrift o = new Opskrift("Salat", "http://example.com/salat", 10, "Skær grøntsager. Bland.", false);

        //Act
        o = dao.insertOpskrift(o);
        boolean deleted = dao.deleteOpskrift(o.getId());
        List<Opskrift> all = dao.getAll();
        int id = o.getId();
        boolean stillExists = all.stream().anyMatch(x -> x.getId() == id);

        //Assert
        assertTrue(deleted, "Sletningen skal returnere true");
        assertFalse(stillExists, "Opskriften skal være slettet");
    }

    @Test
    void testUpdateOpskrift() {
        // Arrange
        Opskrift o = new Opskrift("Peberfrugt", "http://example.com/peberfrugt", 10, "Skær grøntsager. Bland.", false);

        //Act
        o = dao.insertOpskrift(o);
        o.setName("Rød Peberfrugt");
        boolean updated = dao.updateOpskrift(o);
        Opskrift fetched = dao.getById(o.getId());

        // Assert
        assertTrue(updated, "Update skal returnere true hvis opdateringen lykkes");
        assertEquals("Rød Peberfrugt", fetched.getName(), "Navnet skal være opdateret");
    }

    @Test
    void testGetById() {
        //Arrange 
        Opskrift i = new Opskrift("Peberfrugt", "http://example.com/peberfrugt", 10, "Skær grøntsager. Bland.", false);

        //Act
        i = dao.insertOpskrift(i);
        Opskrift fetched = dao.getById(i.getId());

        //Assert
        assertNotNull(fetched, "Ingrediens skal findes via getById");
        assertEquals(i.getId(), fetched.getId(), "ID skal matche");
        assertEquals(i.getName(), fetched.getName(), "Navn skal matche");
    }

    @Test
    void testUpdateNonExisting() {
        //Arrange 
        Opskrift i = new Opskrift(99999, "Fiktiv", "http://example.com/fiktiv", 0, "Ingen instruktioner", false);

        //act
        boolean updated = dao.updateOpskrift(i);

        //Assert
        assertFalse(updated, "Update skal returnere false hvis ingrediens ikke findes");
    }
}
