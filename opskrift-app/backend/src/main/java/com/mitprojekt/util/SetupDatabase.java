package com.mitprojekt.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SetupDatabase {

        public static void initialize() {
    String ingredientSql = """
        CREATE TABLE IF NOT EXISTS Ingredient (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            name TEXT NOT NULL,
            parent_id INTEGER
        );
        """;

    String RecipeSql = """
        CREATE TABLE IF NOT EXISTS Recipe (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            name TEXT NOT NULL,
            link_to_page TEXT,
            time_to_make INTEGER,
            instructions TEXT,
            has_made BOOLEAN DEFAULT FALSE
        );
        """;

    String RecipeIngredientsSql = """
            CREATE TABLE IF NOT EXISTS Recipe_ingredient (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            recipe_id INTEGER NOT NULL,
            ingredient_id INTEGER NOT NULL,
            amount TEXT,
            FOREIGN KEY(recipe_id) REFERENCES Recipe(id) ON DELETE CASCADE,
            FOREIGN KEY(ingredient_id) REFERENCES ingredient(id) ON DELETE CASCADE
            );
            """;

        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(ingredientSql);
            stmt.execute(RecipeSql);
            stmt.execute(RecipeIngredientsSql);
            System.out.println("✅ Database klar (SQLite)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}