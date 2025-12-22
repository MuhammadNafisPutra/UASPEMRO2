package com.example.pemro.Controller;

import com.example.pemro.model.*;
import com.example.pemro.Repository.PostinganRepository;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class PostDetailController {
    @FXML private Label titleLabel, typeLabel, statusLabel, detailLabel;
    @FXML private TextArea descArea;
    @FXML private Button deleteButton, editButton;

    private Postingan post;
    private User user;
    private PostinganRepository repo = new PostinganRepository();

    public void setPostData(Postingan post, User user) {
        this.post = post;
        this.user = user;

        titleLabel.setText(post.getTitle());
        descArea.setText(post.getDescription());
        typeLabel.setText(post.getPostType());

        // Menggunakan method overriding getDetailInfo() (Poin f)
        detailLabel.setText(post.getDetailInfo());

        // Logika akses: Tombol Edit/Hapus hanya muncul untuk pemilik postingan (Logic Business)
        boolean isOwner = (user.getUserId() == post.getUserId());
        deleteButton.setVisible(isOwner);
        editButton.setVisible(isOwner);
    }

    @FXML
    private void handleDelete() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Hapus postingan ini?", ButtonType.YES, ButtonType.NO);
        if (confirm.showAndWait().get() == ButtonType.YES) {
            if (repo.deletePost(post.getId())) {
                ((Stage) titleLabel.getScene().getWindow()).close();
            }
        }
    }
}