package com.mitprojekt.dao;

import com.mitprojekt.model.Ingrediens;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngrediensDAO extends BaseDAO {

    // INSERT
    public Ingrediens insertIngrediens(Ingrediens ingrediens) {
        String sql = "INSERT INTO ingrediens (name, parent_id) VALUES (?, ?)";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, ingrediens.getName());
            if (ingrediens.getParentId() > 0) {
                stmt.setInt(2, ingrediens.getParentId());
            } else {
                stmt.setNull(2, Types.INTEGER);
            }

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        ingrediens.setId(generatedKeys.getInt(1));
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("⚠️ SQL-fejl under insert: " + e.getMessage());
            e.printStackTrace();
            return null;
        }

        return ingrediens;
    }

    // DELETE
    public boolean deleteIngrediens(int ingrediensID) {
        String sql = "DELETE FROM ingrediens WHERE id = ?";
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ingrediensID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // UPDATE
    public boolean updateIngrediens(Ingrediens ingrediens) {
        String sql = "UPDATE ingrediens SET name = ?, parent_id = ? WHERE id = ?";
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ingrediens.getName());

            if (ingrediens.getParentId() > 0) {
                stmt.setInt(2, ingrediens.getParentId());
            } else {
                stmt.setNull(2, Types.INTEGER);
            }

            stmt.setInt(3, ingrediens.getId());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // GET BY ID
    public Ingrediens getById(int ingrediensID) {
        String sql = "SELECT id, name, parent_id FROM ingrediens WHERE id = ?";
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ingrediensID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Ingrediens(
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
    public List<Ingrediens> getAll() {
        List<Ingrediens> ingredients = new ArrayList<>();
        String sql = "SELECT id, name, parent_id FROM ingrediens";

        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ingredients.add(new Ingrediens(
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
