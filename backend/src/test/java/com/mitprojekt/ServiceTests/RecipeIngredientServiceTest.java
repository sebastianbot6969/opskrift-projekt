package com.mitprojekt.ServiceTests;

import com.mitprojekt.dao.RecipeIngredientDAO;
import com.mitprojekt.model.RecipeIngredient;
import com.mitprojekt.service.RecipeIngredientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class RecipeIngredientServiceTest {

    @Mock
    private RecipeIngredientDAO recipeIngredientDAO;

    @InjectMocks
    private RecipeIngredientService recipeIngredientService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddRecipeIngredient() {
        RecipeIngredient ri = new RecipeIngredient(0, 1, 2, "100g");
        RecipeIngredient saved = new RecipeIngredient(10, 1, 2, "100g");

        when(recipeIngredientDAO.insertRecipeIngredient(ri)).thenReturn(saved);

        RecipeIngredient result = recipeIngredientService.addRecipeIngredient(ri);

        assertThat(result.getId()).isEqualTo(10);
        verify(recipeIngredientDAO, times(1)).insertRecipeIngredient(ri);
    }

    @Test
    void testGetRecipeIngredientById() {
        RecipeIngredient ri = new RecipeIngredient(5, 1, 2, "200g");

        when(recipeIngredientDAO.getRecipeIngredientById(5)).thenReturn(ri);

        RecipeIngredient result = recipeIngredientService.getRecipeIngredientById(5);

        assertThat(result.getAmount()).isEqualTo("200g");
        verify(recipeIngredientDAO, times(1)).getRecipeIngredientById(5);
    }

    @Test
    void testGetAllRecipeIngredients() {
        List<RecipeIngredient> list = Arrays.asList(
                new RecipeIngredient(1, 1, 1, "1 cup"),
                new RecipeIngredient(2, 1, 2, "2 tbsp")
        );

        when(recipeIngredientDAO.getAllRecipeIngredients()).thenReturn(list);

        List<RecipeIngredient> result = recipeIngredientService.getAllRecipeIngredients();

        assertThat(result).hasSize(2);
        verify(recipeIngredientDAO, times(1)).getAllRecipeIngredients();
    }

    @Test
    void testUpdateRecipeIngredient() {
        RecipeIngredient ri = new RecipeIngredient(3, 1, 2, "300g");

        when(recipeIngredientDAO.updateRecipeIngredient(ri)).thenReturn(true);

        boolean result = recipeIngredientService.updateRecipeIngredient(ri);

        assertThat(result).isTrue();
        verify(recipeIngredientDAO, times(1)).updateRecipeIngredient(ri);
    }

    @Test
    void testDeleteRecipeIngredient() {
        when(recipeIngredientDAO.deleteRecipeIngredient(7)).thenReturn(true);

        boolean result = recipeIngredientService.deleteRecipeIngredient(7);

        assertThat(result).isTrue();
        verify(recipeIngredientDAO, times(1)).deleteRecipeIngredient(7);
    }
}
