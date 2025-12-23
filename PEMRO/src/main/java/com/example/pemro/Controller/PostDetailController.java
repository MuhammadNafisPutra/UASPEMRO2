package com.example.pemro.Controller;

import com.example.pemro.model.*;
import com.example.pemro.Repository.PostinganRepository;
import com.example.pemro.Repository.TransaksiRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

public class PostDetailController {
    @FXML private Label titleLabel, typeLabel, statusLabel, detail1Label, descLabel;
    @FXML private Button deleteButton, editButton, closeButton;
    @FXML private Button barterButton;
    @FXML private VBox cardContainer;

    private Postingan post;
    private User user;

    private PostinganRepository repo = new PostinganRepository();
    private TransaksiRepository transRepo = new TransaksiRepository();

    public void setPostData(Postingan post, User user) {
        this.post = post;
        this.user = user;
        if (titleLabel != null) titleLabel.setText(post.getTitle());
        if (descLabel != null) descLabel.setText(post.getDescription());
        if (typeLabel != null) typeLabel.setText(post.getPostType());
        if (statusLabel != null) statusLabel.setText(post.getStatus());

        if (detail1Label != null) {
            if (post.getDetailInfo() != null) {
                detail1Label.setText(post.getDetailInfo());
            } else {
                detail1Label.setText("-");
            }
        }

        boolean isOwner = (user != null && user.getUserId() == post.getUserId());

        if (deleteButton != null) {
            deleteButton.setVisible(isOwner);
            deleteButton.setManaged(isOwner);
        }

        if (editButton != null) {
            editButton.setVisible(isOwner);
            editButton.setManaged(isOwner);
        }

        if (barterButton != null) {
            barterButton.setVisible(!isOwner);
            barterButton.setManaged(!isOwner);
        }

        if (closeButton != null) {
            closeButton.setVisible(false);
            closeButton.setManaged(false);
        }
    }

    public void showCloseButton(boolean show) {
        if (closeButton != null) {
            closeButton.setVisible(show);
            closeButton.setManaged(show);
        }
    }

    @FXML
    private void handleBarter() {
        try {
            if (user == null) {
                showAlert(Alert.AlertType.ERROR, "Gagal", "User tidak terdeteksi. Silakan Login ulang.");
                return;
            }
            if (post == null) {
                showAlert(Alert.AlertType.ERROR, "Gagal", "Data postingan tidak valid.");
                return;
            }
            if (transRepo == null) {
                transRepo = new TransaksiRepository();
            }

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                    "Ajukan barter untuk skill ini?", ButtonType.YES, ButtonType.NO);

            if (confirm.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                Transaksi trx = new Transaksi(post.getId(), user.getUserId());
                if (transRepo.ajukanBarter(trx)) {
                    showAlert(Alert.AlertType.INFORMATION, "Sukses", "Permintaan barter berhasil dikirim!");
                    if (barterButton != null) {
                        barterButton.setDisable(true);
                        barterButton.setText("Terkirim");
                    }
                } else {
                    showAlert(Alert.AlertType.ERROR, "Gagal", "Gagal menyimpan ke database. Pastikan tabel 'transaksi' ada.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error Sistem", "Terjadi kesalahan: " + e.getMessage());
        }
    }

    @FXML
    private void handleDelete() {
        if (post == null) return;
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Hapus postingan ini?", ButtonType.YES, ButtonType.NO);
        if (confirm.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            if (repo.deletePost(post.getId())) {
                reloadDashboard();
            } else {
                showAlert(Alert.AlertType.ERROR, "Gagal", "Gagal menghapus postingan.");
            }
        }
    }

    @FXML
    private void handleEdit() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pemro/view/EditPost.fxml"));
            Parent root = loader.load();

            EditPostController controller = loader.getController();
            controller.setPostData(post);
            controller.setUser(user);

            editButton.getScene().setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void reloadDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pemro/view/Dashboard.fxml"));
            Parent root = loader.load();
            DashboardController controller = loader.getController();
            controller.setUserInfo(user);

            if (titleLabel.getScene() != null) {
                titleLabel.getScene().setRoot(root);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleClose() {
        if (titleLabel != null && titleLabel.getScene() != null && titleLabel.getScene().getWindow() instanceof Stage) {
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}