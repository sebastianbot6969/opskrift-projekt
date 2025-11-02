package com.mitprojekt.ServiceTests;

import com.mitprojekt.dao.IngredientDAO;
import com.mitprojekt.model.Ingredient;
import com.mitprojekt.service.IngredientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class IngredientServiceTest {

    @Mock
    private IngredientDAO ingredientDAO;

    @InjectMocks
    private IngredientService ingredientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllIngredients() {
        List<Ingredient> mockList = Arrays.asList(
                new Ingredient("Tomato"),
                new Ingredient( "Cheese")
        );

        when(ingredientDAO.getAll()).thenReturn(mockList);

        List<Ingredient> result = ingredientService.getAllIngredients();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Tomato");
        verify(ingredientDAO, times(1)).getAll();
    }

    @Test
    void testGetIngredientById() {
        Ingredient mockIngredient = new Ingredient( "Basil");

        when(ingredientDAO.getById(1)).thenReturn(mockIngredient);

        Ingredient result = ingredientService.getIngredientById(1);

        assertThat(result.getName()).isEqualTo("Basil");
        verify(ingredientDAO, times(1)).getById(1);
    }

    @Test
    void testCreateIngredient() {
        Ingredient newIngredient = new Ingredient("Garlic");
        Ingredient created = new Ingredient( "Garlic");

        when(ingredientDAO.insertIngredient(newIngredient)).thenReturn(created);

        Ingredient result = ingredientService.createIngredient(newIngredient);

        assertThat(result.getId()).isEqualTo(0);
        verify(ingredientDAO, times(1)).insertIngredient(newIngredient);
    }

    @Test
    void testUpdateIngredient() {
        Ingredient updatedIngredient = new Ingredient( "Red Onion");
        when(ingredientDAO.updateIngredient(updatedIngredient)).thenReturn(true);

        Ingredient result = ingredientService.updateIngredient(1, updatedIngredient);

        assertNull(result);
    }

    @Test
    void testDeleteIngredient() {
        when(ingredientDAO.deleteIngredient(1)).thenReturn(true);

        boolean result = ingredientService.deleteIngredient(1);

        assertThat(result).isTrue();
        verify(ingredientDAO, times(1)).deleteIngredient(1);
    }
}
