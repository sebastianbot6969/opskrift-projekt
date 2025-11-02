package com.mitprojekt.dao;

import com.mitprojekt.model.Ingredient;
import com.mitprojekt.model.Recipe;
import com.mitprojekt.model.RecipeIngredient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class RecipeDAO extends BaseDAO {

public Recipe insertRecipe(Recipe recipe) {
    String sql = "INSERT INTO recipe (name, link_to_page, time_to_make, instructions, has_made) VALUES (?, ?, ?, ?, ?)";

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

        stmt.setString(1, recipe.getName());
        stmt.setString(2, recipe.getLinkToPage());
        stmt.setInt(3, recipe.getTime());
        stmt.setString(4, recipe.getProcedure());
        stmt.setBoolean(5, recipe.getHasMade());

        int affectedRows = stmt.executeUpdate();
        if (affectedRows == 0) return null; // Hvis insert fejler

        try (ResultSet keys = stmt.getGeneratedKeys()) {
            if (keys.next()) {
                recipe.setId(keys.getInt(1));
            } else {
                return null;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return null;
    }

    return recipe;
}


    // DELETE
    public boolean deleteRecipe(int recipeID) {
        String sql = "DELETE FROM recipe WHERE id = ?";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, recipeID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    // UPDATE
    public boolean updateRecipe(Recipe recipe) {
        String sql = """
                UPDATE recipe
                SET name = ?, link_to_page = ?, time_to_make = ?, instructions = ?, has_made = ?
                WHERE id = ?
                """;

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, recipe.getName());
            stmt.setString(2, recipe.getLinkToPage());
            stmt.setInt(3, recipe.getTime());
            stmt.setString(4, recipe.getProcedure());
            stmt.setBoolean(5, recipe.getHasMade());
            stmt.setInt(6, recipe.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // GET BY ID
    public Recipe getById(int recipeID) {
        String sql = """
                SELECT id, name, link_to_page, time_to_make, instructions, has_made
                FROM recipe WHERE id = ?
                """;

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, recipeID);
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
        String sql = """
                SELECT id, name, link_to_page, time_to_make, instructions, has_made
                FROM recipe
                """;

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

    public List<Recipe> getRecipesByIngredient(int ingredientId) {
        String sql = """
                    SELECT r.* FROM recipe r
                    JOIN recipe_ingredient ri ON r.id = ri.recipe_id
                    WHERE ri.ingredient_id = ?
                """;
        // execute SQL og returner opskrifter
        List<Recipe> recipes = new ArrayList<>();
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ingredientId);
            ResultSet rs = stmt.executeQuery();

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
            return null;
        }
        return recipes;
    }

    public boolean addIngredientToRecipe(int recipeId, Ingredient ingredient) {
        String sql = """
                INSERT INTO recipe_ingredient (recipe_id, ingredient_id, amount) VALUES (?, ?, ?)
                """;
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, recipeId);
            stmt.setInt(2, ingredient.getId());
            stmt.setString(3, "to taste"); // Placeholder amount
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
