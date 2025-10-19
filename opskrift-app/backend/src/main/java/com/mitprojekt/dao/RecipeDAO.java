package com.mitprojekt.dao;

import com.mitprojekt.model.Recipe;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecipeDAO extends BaseDAO {

    // INSERT
    public Recipe insertRecipe(Recipe Recipe) {
        String sql = "INSERT INTO Recipe (name, link_to_page, time_to_make, instructions, has_made) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, Recipe.getName());
            stmt.setString(2, Recipe.getLinkToPage());
            stmt.setInt(3, Recipe.getTime());
            stmt.setString(4, Recipe.getProcedure());
            stmt.setBoolean(5, Recipe.getHasMade());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        Recipe.setId(generatedKeys.getInt(1));
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("⚠️ SQL-fejl under insert: " + e.getMessage());
            e.printStackTrace();
            return null;
        }

        return Recipe;
    }

    //DELETE
    public boolean deleteRecipe(int RecipeID) {
        String sql = "DELETE FROM Recipe WHERE id = ?";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, RecipeID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    // UPDATE
    public boolean updateRecipe(Recipe Recipe) {
        String sql = "UPDATE Recipe SET name = ?, link_to_page = ?, time_to_make = ?, instructions = ?, has_made = ? WHERE id = ?";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, Recipe.getName());
            stmt.setString(2, Recipe.getLinkToPage());
            stmt.setInt(3, Recipe.getTime());
            stmt.setString(4, Recipe.getProcedure());
            stmt.setBoolean(5, Recipe.getHasMade());
            stmt.setInt(6, Recipe.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // GET BY ID
    public Recipe getById(int RecipeID) {
        String sql = "SELECT id, name, link_to_page, time_to_make, instructions, has_made FROM Recipe WHERE id = ?";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, RecipeID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Recipe(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("link_to_page"),
                        rs.getInt("time_to_make"),
                        rs.getString("instructions"),
                        rs.getBoolean("has_made"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // get all
    public List<Recipe> getAll() {
        List<Recipe> recipes = new ArrayList<>();
        String sql = "SELECT id, name, link_to_page, time_to_make, instructions, has_made FROM Recipe";

        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                recipes.add(new Recipe(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("link_to_page"),
                        rs.getInt("time_to_make"),
                        rs.getString("instructions"),
                        rs.getBoolean("has_made")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recipes;
    }
}
