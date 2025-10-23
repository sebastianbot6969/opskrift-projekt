package com.mitprojekt.DAOTests;

import com.mitprojekt.dao.IngredientDAO;
import com.mitprojekt.model.Ingredient;

import java.util.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class IngredientDAOTest {

    static IngredientDAO dao;

    @BeforeAll
    static void setup() {
        dao = new IngredientDAO();
    }

    @Test
    void testInsertIngredient() {
        //Arrange
        Ingredient i = new Ingredient("Tomat");

        //Act
        Ingredient result = dao.insertIngredient(i);

        //Assert
        assertTrue(result.getId() > 0, "Efter insert skal id sættes af databasen");
    }

    @Test
    void testGetAllReturnsList() {
        //Arrange
        List<Ingredient> list = dao.getAll();

        //Assert
        assertNotNull(list, "Listen må ikke være null");
    }

    @Test
    void testInsertAndFetch() {
        //Arrange
        Ingredient i = new Ingredient("Løg");
        
        //Act
        dao.insertIngredient(i);
        List<Ingredient> all = dao.getAll();
        boolean found = all.stream().anyMatch(x -> x.getName().equals("Løg"));

        //Assert
        assertTrue(found, "Den indsatte Ingredient skal findes i databasen");
    }

    @Test
    void testDeleteIngredient() {
        //Arrange
        Ingredient i = new Ingredient("Hvidløg");
        
        //Act
        i = dao.insertIngredient(i);
        boolean deleted = dao.deleteIngredient(i.getId());

        List<Ingredient> all = dao.getAll();
        int id = i.getId();
        boolean stillExists = all.stream().anyMatch(x -> x.getId() == id);
        
        //Assert
        assertTrue(deleted, "Sletningen skal returnere true");
        assertFalse(stillExists, "Ingredienten skal være slettet");
    }

    @Test
    void testUpdateIngredient() {
        // Arrange
        Ingredient i = new Ingredient("Peberfrugt");
        
        // Act
        i = dao.insertIngredient(i);
        i.setName("Rød Peberfrugt");
        i.setParentId(0); 
        boolean updated = dao.updateIngredient(i);
        Ingredient fetched = dao.getById(i.getId());

        // Assert
        assertTrue(updated, "Update skal returnere true hvis opdateringen lykkes");
        assertEquals("Rød Peberfrugt", fetched.getName(), "Navnet skal være opdateret");
        assertEquals(0, fetched.getParentId(), "ParentId skal være opdateret");
    }

    @Test
    void testGetById() {
        // Arrange
        Ingredient i = new Ingredient("Lime");
        
        // Act
        i = dao.insertIngredient(i);
        Ingredient fetched = dao.getById(i.getId());

        // Assert
        assertNotNull(fetched, "Ingredient skal findes via getById");
        assertEquals(i.getId(), fetched.getId(), "ID skal matche");
        assertEquals(i.getName(), fetched.getName(), "Navn skal matche");
    }

    @Test
    void testUpdateNonExisting() {
        // Arrange
        Ingredient i = new Ingredient(99999, "Fiktiv", 0);

        // Act
        boolean updated = dao.updateIngredient(i);
        
        // Assert
        assertFalse(updated, "Update skal returnere false hvis Ingredient ikke findes");
    }
}
