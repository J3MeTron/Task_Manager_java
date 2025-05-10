package com.taskmanager.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;

@WebListener
public class DBInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            // Создаем таблицы при запуске приложения
            try (Connection conn = DBUtil.getConnection();
                    Statement stmt = conn.createStatement()) {

                // Читаем SQL-скрипт из ресурсов
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(getClass().getResourceAsStream("/schema.sql")))) {

                    StringBuilder sql = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        // Пропускаем пустые строки и комментарии
                        if (line.trim().isEmpty() || line.trim().startsWith("--")) {
                            continue;
                        }

                        sql.append(line);
                        if (line.trim().endsWith(";")) {
                            try {
                                stmt.execute(sql.toString());
                            } catch (Exception e) {
                                System.err.println("Error executing SQL: " + sql.toString());
                                throw e;
                            }
                            sql.setLength(0);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error initializing database", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Ничего не делаем при остановке приложения
    }
}