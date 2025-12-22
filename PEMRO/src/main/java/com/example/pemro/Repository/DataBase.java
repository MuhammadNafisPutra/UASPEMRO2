package com.example.pemro.Repository;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataBase {
    // 1. Pastikan nama database sesuai (barterskill)
    private static final String URL = "jdbc:mysql://localhost:3306/barterskill";

    // 2. Pastikan user biasanya 'root'
    private static final String USER = "root";

    // 3. PENTING: Untuk XAMPP default, password biasanya KOSONG ("")
    // Jika kamu set password di MySQL, masukkan di sini.
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            // Ini akan muncul di Console IntelliJ jika gagal konek
            System.out.println("Gagal koneksi: " + e.getMessage());
            return null;
        }
    }
}