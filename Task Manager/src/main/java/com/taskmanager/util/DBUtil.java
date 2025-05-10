package com.taskmanager.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.File;

public class DBUtil {
    private static final String DB_URL = "jdbc:sqlite:task_manager.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
            // Проверяем существование директории для базы данных
            File dbFile = new File("task_manager.db");
            if (!dbFile.exists()) {
                System.out.println("Creating new database file: " + dbFile.getAbsolutePath());
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Error loading SQLite driver: " + e.getMessage());
            throw new RuntimeException("Error loading SQLite driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            // Включаем поддержку внешних ключей
            conn.createStatement().execute("PRAGMA foreign_keys = ON;");
            return conn;
        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
            throw e;
        }
    }
}