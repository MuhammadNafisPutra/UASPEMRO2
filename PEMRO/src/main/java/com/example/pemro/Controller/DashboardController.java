package com.example.pemro.Controller;

import com.example.pemro.Repository.PostinganRepository;
import com.example.pemro.model.Postingan;
import com.example.pemro.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class DashboardController {
    @FXML private Label welcomeLabel;
    @FXML private ListView<String> postListView;
    @FXML private TextField searchField;

    private User loggedInUser;
    private PostinganRepository postRepo = new PostinganRepository();
    private List<Postingan> currentPostList;

    public void setUserInfo(User user) {
        this.loggedInUser = user;
        if (user != null) {
            welcomeLabel.setText("Halo, " + user.getFullName() + "!");
        }
        refreshPostList(); // Load data setelah user diset
    }

    @FXML
    public void initialize() {
        postListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                int index = postListView.getSelectionModel().getSelectedIndex();
                if (index >= 0 && index < currentPostList.size()) {
                    openPostDetail(currentPostList.get(index));
                }
            }
        });
    }

    private void refreshPostList() {
        postListView.getItems().clear();
        currentPostList = postRepo.loadAllPosts();
        for (Postingan p : currentPostList) {
            // Menggunakan method polimorfisme getDetailInfo() yang kita buat di Step 1
            postListView.getItems().add("[" + p.getPostType() + "] " + p.getTitle() + " - " + p.getStatus());
        }
    }

    private void openPostDetail(Postingan post) {
        try {
            // Pastikan path FXML lengkap (Poin i)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pemro/view/PostDetail.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));

            PostDetailController controller = loader.getController();
            controller.setPostData(post, loggedInUser);

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            refreshPostList(); // Refresh list setelah kembali dari detail (jika ada hapus/edit)
        } catch (IOException e) { e.printStackTrace(); }
    }

    @FXML private void handleSearch() {
        String keyword = searchField.getText();
        currentPostList = postRepo.searchPosts(keyword);
        postListView.getItems().clear();
        for (Postingan p : currentPostList) {
            postListView.getItems().add("[" + p.getPostType() + "] " + p.getTitle());
        }
    }
}