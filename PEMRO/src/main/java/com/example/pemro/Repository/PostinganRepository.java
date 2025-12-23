package com.example.pemro.Repository;

import com.example.pemro.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostinganRepository implements PostRepositoryInterface {

    @Override
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
                Offer o = (Offer) post;
                stmt.setInt(6, o.getExperienceYears());
                stmt.setString(7, o.getPortfolioLink());
                stmt.setNull(8, Types.DATE);
                stmt.setNull(9, Types.VARCHAR);
            } else {
                Request r = (Request) post;
                stmt.setInt(6, 0);
                stmt.setNull(7, Types.VARCHAR);
                stmt.setDate(8, r.getDeadline());
                stmt.setString(9, r.getUrgencyLevel());
            }

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    post.setId(rs.getInt(1));
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Postingan> loadAllPosts() {
        List<Postingan> posts = new ArrayList<>();
        String sql = "SELECT * FROM skill_posts";
        try (Connection conn = DataBase.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                posts.add(mapResultSetToPost(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public List<Postingan> searchPosts(String keyword) {
        List<Postingan> posts = new ArrayList<>();
        String sql = "SELECT * FROM skill_posts WHERE title LIKE ? OR description LIKE ?";
        try (Connection conn = DataBase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                posts.add(mapResultSetToPost(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
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

    @Override
    public boolean updatePost(Postingan post) {
        String sql = "UPDATE skill_posts SET title = ?, description = ?, status = ?, experience_years = ?, portfolio_link = ?, deadline = ?, urgency_level = ? WHERE post_id = ?";
        try (Connection conn = DataBase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, post.getTitle());
            stmt.setString(2, post.getDescription());
            stmt.setString(3, post.getStatus());

            if (post instanceof Offer) {
                Offer o = (Offer) post;
                stmt.setInt(4, o.getExperienceYears());
                stmt.setString(5, o.getPortfolioLink());
                stmt.setNull(6, Types.DATE);
                stmt.setNull(7, Types.VARCHAR);
            } else {
                Request r = (Request) post;
                stmt.setInt(4, 0);
                stmt.setNull(5, Types.VARCHAR);
                stmt.setDate(6, r.getDeadline());
                stmt.setString(7, r.getUrgencyLevel());
            }
            stmt.setInt(8, post.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Postingan mapResultSetToPost(ResultSet rs) throws SQLException {
        Postingan p;
        if ("OFFER".equals(rs.getString("post_type"))) {
            p = new Offer(
                    rs.getInt("user_id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getInt("experience_years"),
                    rs.getString("portfolio_link")
            );
        } else {
            p = new Request(
                    rs.getInt("user_id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getDate("deadline"),
                    rs.getString("urgency_level")
            );
        }
        p.setId(rs.getInt("post_id"));
        p.setStatus(rs.getString("status"));
        return p;
    }
}
