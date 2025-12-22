package com.example.pemro.Repository;

import com.example.pemro.model.Offer;
import com.example.pemro.model.Postingan;
import com.example.pemro.model.Request;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostinganRepository {

    public boolean savePost(Postingan post) {
        String sql = "INSERT INTO skill_posts (user_id, title, description, post_type, status, experience_years, portfolio_link, deadline, urgency_level) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DataBase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, post.getUserId());
            stmt.setString(2, post.getTitle());
            stmt.setString(3, post.getDescription());
            stmt.setString(4, post.getPostType());
            stmt.setString(5, post.getStatus());

            if (post instanceof Offer) {
                Offer offer = (Offer) post;
                stmt.setInt(6, offer.getExperienceYears());
                stmt.setString(7, offer.getPortfolioLink());
                stmt.setNull(8, Types.DATE);
                stmt.setNull(9, Types.VARCHAR);
            } else if (post instanceof Request) {
                Request req = (Request) post;
                stmt.setInt(6, 0);
                stmt.setNull(7, Types.VARCHAR);
                stmt.setDate(8, req.getDeadline());
                stmt.setString(9, req.getUrgencyLevel());
            }

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                ResultSet keys = stmt.getGeneratedKeys();
                if (keys.next()) {
                    post.setId(keys.getInt(1));
                }
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePost(Postingan post) {
        String sql = "UPDATE skill_posts SET title=?, description=?, experience_years=?, portfolio_link=?, deadline=?, urgency_level=? WHERE post_id=?";
        try (Connection conn = DataBase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, post.getTitle());
            stmt.setString(2, post.getDescription());

            if (post instanceof Offer) {
                Offer offer = (Offer) post;
                stmt.setInt(3, offer.getExperienceYears());
                stmt.setString(4, offer.getPortfolioLink());
                stmt.setNull(5, Types.DATE);
                stmt.setNull(6, Types.VARCHAR);
            } else if (post instanceof Request) {
                Request req = (Request) post;
                stmt.setInt(3, 0);
                stmt.setNull(4, Types.VARCHAR);
                stmt.setDate(5, req.getDeadline());
                stmt.setString(6, req.getUrgencyLevel());
            }

            stmt.setInt(7, post.getId());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePost(int postId) {
        String sql = "DELETE FROM skill_posts WHERE post_id = ?";
        try (Connection conn = DataBase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, postId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
