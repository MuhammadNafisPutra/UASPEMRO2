package com.example.pemro.Repository;

import com.example.pemro.model.Transaksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransaksiRepository {

    public boolean ajukanBarter(Transaksi trx) {
        String sql = "INSERT INTO transaksi (postingan_id, peminat_id, status, tanggal) VALUES (?, ?, ?, ?)";

        try (Connection conn = DataBase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, trx.getPostinganId());
            stmt.setInt(2, trx.getPeminatId());
            stmt.setString(3, trx.getStatus());
            stmt.setDate(4, trx.getTanggal());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Transaksi> getPermintaanMasuk(int userId) {
        List<Transaksi> list = new ArrayList<>();
        String sql = "SELECT t.* FROM transaksi t " +
                "JOIN skill_posts p ON t.postingan_id = p.post_id " +
                "WHERE p.user_id = ? AND t.status = 'PENDING'";

        try (Connection conn = DataBase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Transaksi(
                        rs.getInt("id"),
                        rs.getInt("postingan_id"),
                        rs.getInt("peminat_id"),
                        rs.getString("status"),
                        rs.getDate("tanggal")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateStatus(int transId, String status) {
        String sql = "UPDATE transaksi SET status = ? WHERE id = ?";
        try (Connection conn = DataBase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, transId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}