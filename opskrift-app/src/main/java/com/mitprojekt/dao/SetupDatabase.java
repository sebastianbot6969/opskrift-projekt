package com.mitprojekt.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SetupDatabase {

        public static void initialize() {
    String ingrediensSql = """
        CREATE TABLE IF NOT EXISTS ingrediens (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            name TEXT NOT NULL,
            parent_id INTEGER
        );
        """;

    String opskriftSql = """
        CREATE TABLE IF NOT EXISTS opskrift (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            name TEXT NOT NULL,
            link_to_page TEXT,
            time_to_make INTEGER,
            instructions TEXT,
            has_made BOOLEAN DEFAULT FALSE
        );
        """;


        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(ingrediensSql);
            stmt.execute(opskriftSql);
            System.out.println("✅ Database klar (SQLite)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}