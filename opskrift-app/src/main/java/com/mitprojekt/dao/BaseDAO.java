package com.mitprojekt.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;


public abstract class BaseDAO {
    protected Connection getConnection() throws SQLException {
        return Database.connect();
    }

    protected int findParentId(String table, String name) {
        // Søger efter parent_id baseret på navnet, for alle tabeller der har parent_id
        String sql = "SELECT id FROM " + table + "ingrediens WHERE name = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
