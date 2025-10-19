package com.mitprojekt;

import com.mitprojekt.dao.RecipeDAO;
import com.mitprojekt.model.Recipe;

import java.util.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class RecipeDAOTest {

    static RecipeDAO dao;

    @BeforeAll
    static void setup() {
        dao = new RecipeDAO();
    }

    @Test
    void testInsertRecipe() {
        // Arrange
        Recipe o = new Recipe(1, "Spaghetti Bolognese", "http://example.com", 30, "Kog pasta. Lav sauce.", true);

        // Act
        Recipe result = dao.insertRecipe(o);

        // Assert
        assertTrue(result.getId() > 0, "Efter insert skal id sættes af databasen");
    }

    @Test
    void testGetAllReturnsList() {
        // Arrange

        // Act
        List<Recipe> list = dao.getAll();
        assertNotNull(list, "Listen må ikke være null");
    }

    @Test
    void testInsertAndFetch() {
        // Arrange
        Recipe o = new Recipe(1, "Hakket tomat pasta", "http://example.com", 25, "Kog pasta. Hak tomater.", false);
        
        // Act
        dao.insertRecipe(o);
        List<Recipe> all = dao.getAll();
        boolean found = all.stream().anyMatch(x -> x.getName().equals("Hakket tomat pasta"));
        

        // Assert
        assertTrue(found, "Den indsatte Recipe skal findes i databasen");
    }
    @Test
    void testDeleteRecipe() {
        // Arrange
        Recipe o = new Recipe(1, "Test Recipe", "http://example.com", 20, "Procedure", false);
        o = dao.insertRecipe(o);
        
        // Act
        boolean deleted = dao.deleteRecipe(o.getId());

        // Assert
        assertTrue(deleted, "Den slettede Recipe skal findes i databasen");
    }
    @Test
    void testUpdateRecipe() {
        // Arrange
        Recipe o = new Recipe(1, "Opdateret Recipe", "http://example.com", 15, "Procedure", false);
        o = dao.insertRecipe(o);
        o.setName("Opdateret Recipe Navn");

        // Act
        boolean updated = dao.updateRecipe(o);

        // Assert
        assertTrue(updated, "Den opdaterede Recipe skal findes i databasen");
    }
    @Test
    void testGetById() {
        // Arrange
        Recipe o = new Recipe(1, "Specifik Recipe", "http://example.com", 10, "Procedure", false);
        o = dao.insertRecipe(o);    

        // Act
        Recipe fetched = dao.getById(o.getId());

        // Assert
        assertNotNull(fetched, "Den hentede Recipe må ikke være null");
        assertEquals("Specifik Recipe", fetched.getName(), "Navnet på den hentede Recipe skal matche");
    }
}
