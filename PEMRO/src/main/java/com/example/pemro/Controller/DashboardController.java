package com.example.pemro.Controller;

import com.example.pemro.Repository.PostinganRepository;
import com.example.pemro.model.Postingan;
import com.example.pemro.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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

    @FXML
    public void initialize() {
        refreshPostList();

        postListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                String selectedItem = postListView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    int index = postListView.getSelectionModel().getSelectedIndex();
                    if (index >= 0 && index < currentPostList.size()) {
                        Postingan selectedPost = currentPostList.get(index);
                        openPostDetail(selectedPost);
                    }
                }
            }
        });
    }

    public void setUserInfo(User user) {
        this.loggedInUser = user;
        if (user != null) {
            welcomeLabel.setText("Halo, " + user.getFullName() + "!");
        }
    }

    @FXML
    private void handleRefresh() {
        searchField.setText("");
        refreshPostList();
    }

    private void refreshPostList() {
        postListView.getItems().clear();
        currentPostList = postRepo.loadAllPosts();

        for (Postingan p : currentPostList) {
            String info = String.format("[%s] %s - %s",
                    p.getPostType(),
                    p.getTitle(),
                    p.getStatus());
            postListView.getItems().add(info);
        }
    }

    @FXML
    private void handleSearch() {
        String keyword = searchField.getText();

        if (keyword == null || keyword.trim().isEmpty()) {
            refreshPostList();
            return;
        }

        postListView.getItems().clear();
        currentPostList = postRepo.searchPosts(keyword);

        for (Postingan p : currentPostList) {
            String info = String.format("[%s] %s - %s",
                    p.getPostType(),
                    p.getTitle(),
                    p.getStatus());
            postListView.getItems().add(info);
        }
    }

    @FXML
    private void handleCreatePost() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CreatePost.fxml"));
            Scene scene = new Scene(loader.load());

            CreatePostController controller = loader.getController();
            controller.setUser(loggedInUser);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Buat Postingan Baru");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            refreshPostList();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openPostDetail(Postingan post) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PostDetail.fxml"));
            Scene scene = new Scene(loader.load());

            PostDetailController controller = loader.getController();
            controller.setPostData(post, loggedInUser);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Detail Postingan");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            refreshPostList();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
