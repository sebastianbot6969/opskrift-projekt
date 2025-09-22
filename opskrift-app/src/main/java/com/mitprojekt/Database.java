package com.mitprojekt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:postgresql://localhost:5432/opskrifter";
    private static final String USER = "opskrift_user";
    private static final String PASSWORD = "hemmeligt";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }   
}
