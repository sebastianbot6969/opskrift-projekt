package com.mitprojekt.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:sqlite:Recipe.db";

    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println("⚠️ Fejl ved forbindelse til SQLite: " + e.getMessage());
            return null;
        }
    }
}

