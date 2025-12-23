package com.example.pemro.Controller;

import com.example.pemro.Repository.PostinganRepository;
import com.example.pemro.Repository.TransaksiRepository;
import com.example.pemro.model.Postingan;
import com.example.pemro.model.Transaksi;
import com.example.pemro.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class DashboardController {
    @FXML private Label welcomeLabel;
    @FXML private TextField searchField;
    @FXML private VBox postsContainer;

    private User loggedInUser;
    private PostinganRepository postRepo = new PostinganRepository();
    private TransaksiRepository transRepo = new TransaksiRepository();

    public void setUserInfo(User user) {
        this.loggedInUser = user;
        if (user != null && welcomeLabel != null) {
            welcomeLabel.setText("Halo, " + user.getFullName() + "!");
        }
        refreshPostList();
    }

    @FXML
    public void initialize() {
    }

    @FXML
    private void handleRefresh() {
        refreshPostList();
    }

    private void refreshPostList() {
        if (postsContainer == null) return;
        postsContainer.getChildren().clear();
        List<Postingan> posts = postRepo.loadAllPosts();
        for (Postingan p : posts) {
            addPostToView(p);
        }
    }

    @FXML
    private void handleSearch() {
        String keyword = searchField.getText();
        postsContainer.getChildren().clear();
        List<Postingan> posts;
        if (keyword == null || keyword.isEmpty()) {
            posts = postRepo.loadAllPosts();
        } else {
            posts = postRepo.searchPosts(keyword);
        }
        for (Postingan p : posts) {
            addPostToView(p);
        }
    }

    private void addPostToView(Postingan p) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pemro/view/PostDetail.fxml"));
            Node postCard = loader.load();
            PostDetailController controller = loader.getController();
            controller.setPostData(p, loggedInUser);
            postsContainer.getChildren().add(postCard);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCreatePost(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pemro/view/CreatePost.fxml"));
            Parent root = loader.load();

            CreatePostController controller = loader.getController();
            controller.setUser(loggedInUser);

            Scene currentScene = ((Node) event.getSource()).getScene();
            currentScene.setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pemro/view/Login.fxml"));
            Stage stage = (Stage) searchField.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.setMaximized(true);
            stage.setTitle("BarterSkill - Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCekPermintaan() {
        if (loggedInUser == null) return;

        List<Transaksi> requests = transRepo.getPermintaanMasuk(loggedInUser.getUserId());

        if (requests.isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "Belum ada permintaan barter baru.").show();
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Permintaan Masuk");
        dialog.setHeaderText("Daftar user yang ingin barter dengan Anda:");

        ListView<String> listView = new ListView<>();
        for (Transaksi t : requests) {
            listView.getItems().add("Request ID: " + t.getId() + " - Tgl: " + t.getTanggal());
        }

        VBox content = new VBox(10, listView, new Label("Pilih item lalu klik Aksi di bawah."));
        dialog.getDialogPane().setContent(content);

        ButtonType terimaBtn = new ButtonType("Terima", ButtonBar.ButtonData.OK_DONE);
        ButtonType tolakBtn = new ButtonType("Tolak", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(terimaBtn, tolakBtn);

        dialog.showAndWait().ifPresent(response -> {
            int selectedIdx = listView.getSelectionModel().getSelectedIndex();
            if (selectedIdx >= 0) {
                Transaksi selectedTrx = requests.get(selectedIdx);
                if (response == terimaBtn) {
                    transRepo.updateStatus(selectedTrx.getId(), "ACCEPTED");
                    new Alert(Alert.AlertType.INFORMATION, "Barter Diterima! Silakan hubungi peminat.").show();
                } else if (response == tolakBtn) {
                    transRepo.updateStatus(selectedTrx.getId(), "REJECTED");
                    new Alert(Alert.AlertType.INFORMATION, "Barter Ditolak.").show();
                }
            }
        });
    }
}