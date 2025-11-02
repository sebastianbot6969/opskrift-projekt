package com.mitprojekt.dao;

import com.mitprojekt.model.RecipeIngredient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class RecipeIngredientDAO extends BaseDAO {

    // INSERT
    public RecipeIngredient insertRecipeIngredient(RecipeIngredient recipeIngredient) {
        String sql = "INSERT INTO recipe_ingredient (recipe_id, ingredient_id, amount) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, recipeIngredient.getRecipeId());
            stmt.setInt(2, recipeIngredient.getIngredientId());
            stmt.setString(3, recipeIngredient.getAmount());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        recipeIngredient.setId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return recipeIngredient;
    }

    // DELETE
    public boolean deleteRecipeIngredient(int id) {
        String sql = "DELETE FROM recipe_ingredient WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // UPDATE
    public boolean updateRecipeIngredient(RecipeIngredient recipeIngredient) {
        String sql = "UPDATE recipe_ingredient SET recipe_id = ?, ingredient_id = ?, amount = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, recipeIngredient.getRecipeId());
            stmt.setInt(2, recipeIngredient.getIngredientId());
            stmt.setString(3, recipeIngredient.getAmount());
            stmt.setInt(4, recipeIngredient.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // GET BY ID
    public RecipeIngredient getRecipeIngredientById(int id) {
        String sql = "SELECT id, recipe_id, ingredient_id, amount FROM recipe_ingredient WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new RecipeIngredient(
                        rs.getInt("id"),
                        rs.getInt("recipe_id"),
                        rs.getInt("ingredient_id"),
                        rs.getString("amount"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // GET ALL
    public List<RecipeIngredient> getAllRecipeIngredients() {
        List<RecipeIngredient> ingredients = new ArrayList<>();
        String sql = "SELECT id, recipe_id, ingredient_id, amount FROM recipe_ingredient";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ingredients.add(new RecipeIngredient(
                        rs.getInt("id"),
                        rs.getInt("recipe_id"),
                        rs.getInt("ingredient_id"),
                        rs.getString("amount")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingredients;
    }
}
