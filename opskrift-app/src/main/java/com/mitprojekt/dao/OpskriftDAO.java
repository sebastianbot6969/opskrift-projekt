package com.mitprojekt.dao;

import com.mitprojekt.model.Opskrift;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OpskriftDAO extends BaseDAO {

    // INSERT
    public Opskrift insertOpskrift(Opskrift opskrift) {
        String sql = "INSERT INTO opskrift (name, link_to_page, time_to_make, instructions, has_made) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, opskrift.getName());
            stmt.setString(2, opskrift.getLinkToPage());
            stmt.setInt(3, opskrift.getTime());
            stmt.setString(4, opskrift.getProcedure());
            stmt.setBoolean(5, opskrift.getHasMade());

            System.out.println("stmt: " + stmt);
            int affectedRows = stmt.executeUpdate();
            System.out.println("affectedRows: " + affectedRows);
            if (affectedRows > 0) {
                System.out.println("affectedRows: " + affectedRows);
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        opskrift.setId(generatedKeys.getInt(1));
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("⚠️ SQL-fejl under insert: " + e.getMessage());
            e.printStackTrace();
            return null;
        }

        return opskrift;
    }

    //DELETE
    public boolean deleteOpskrift(int opskriftID) {
        String sql = "DELETE FROM opskrift WHERE id = ?";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, opskriftID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    // UPDATE
    public boolean updateOpskrift(Opskrift opskrift) {
        String sql = "UPDATE opskrift SET name = ?, link_to_page = ?, time_to_make = ?, instructions = ?, has_made = ? WHERE id = ?";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, opskrift.getName());
            stmt.setString(2, opskrift.getLinkToPage());
            stmt.setInt(3, opskrift.getTime());
            stmt.setString(4, opskrift.getProcedure());
            stmt.setBoolean(5, opskrift.getHasMade());
            stmt.setInt(6, opskrift.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // GET BY ID
    public Opskrift getById(int opskriftID) {
        String sql = "SELECT id, name, link_to_page, time_to_make, instructions, has_made FROM opskrift WHERE id = ?";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, opskriftID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Opskrift(
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
    public List<Opskrift> getAll() {
        List<Opskrift> opskrifter = new ArrayList<>();
        String sql = "SELECT id, name, link_to_page, time_to_make, instructions, has_made FROM opskrift";

        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                opskrifter.add(new Opskrift(
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

        return opskrifter;
    }
}
