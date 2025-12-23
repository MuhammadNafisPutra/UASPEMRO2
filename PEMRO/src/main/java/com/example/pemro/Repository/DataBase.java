package com.example.pemro.Repository;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataBase {
    private static final String URL = "jdbc:mysql://localhost:3306/barterskill";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            System.out.println("Gagal koneksi: " + e.getMessage());
            return null;
        }
    }
}