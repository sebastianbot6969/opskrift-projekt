package com.mitprojekt.dao;

import com.mitprojekt.model.Ingredient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngredientDAO extends BaseDAO {

    // INSERT
    public Ingredient insertIngredient(Ingredient ingredient) {
        String sql = "INSERT INTO Ingredient (name, parent_id) VALUES (?, ?)";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, ingredient.getName());
            if (ingredient.getParentId() > 0) {
                stmt.setInt(2, ingredient.getParentId());
            } else {
                stmt.setNull(2, Types.INTEGER);
            }

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        ingredient.setId(generatedKeys.getInt(1));
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("⚠️ SQL-fejl under insert: " + e.getMessage());
            e.printStackTrace();
            return null;
        }

        return ingredient;
    }

    // DELETE
    public boolean deleteIngredient(int ingredientID) {
        String sql = "DELETE FROM Ingredient WHERE id = ?";
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ingredientID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // UPDATE
    public boolean updateIngredient(Ingredient ingredient) {
        String sql = "UPDATE Ingredient SET name = ?, parent_id = ? WHERE id = ?";
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ingredient.getName());

            if (ingredient.getParentId() > 0) {
                stmt.setInt(2, ingredient.getParentId());
            } else {
                stmt.setNull(2, Types.INTEGER);
            }

            stmt.setInt(3, ingredient.getId());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // GET BY ID
    public Ingredient getById(int ingredientID) {
        String sql = "SELECT id, name, parent_id FROM Ingredient WHERE id = ?";
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ingredientID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Ingredient(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("parent_id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // GET ALL
    public List<Ingredient> getAll() {
        List<Ingredient> ingredients = new ArrayList<>();
        String sql = "SELECT id, name, parent_id FROM Ingredient";

        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ingredients.add(new Ingredient(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("parent_id")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ingredients;
    }
}
