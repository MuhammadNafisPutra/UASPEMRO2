package com.example.pemro.Repository;

import com.example.pemro.model.Postingan;
import java.util.List;

public interface PostRepositoryInterface {
    boolean savePost(Postingan post);
    List<Postingan> loadAllPosts();
    boolean deletePost(int postId);
    boolean updatePost(Postingan post);
    List<Postingan> searchPosts(String keyword);
}