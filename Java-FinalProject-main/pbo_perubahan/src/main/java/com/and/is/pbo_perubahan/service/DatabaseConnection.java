package com.and.is.pbo_perubahan.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mariadb://localhost:3306/todo_db";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Ganti dengan password MySQL kamu

    static {
        try {
            // Register MariaDB JDBC driver secara eksplisit
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}
