package com.mitprojekt.dao;

import com.mitprojekt.util.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public abstract class BaseDAO {
    protected Connection getConnection() throws SQLException {
        return Database.connect();
    }

    protected int findParentId(String table, String name) {
        String sql = "SELECT id FROM " + table + "ingredient WHERE name = ?";
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
