package com.example.pemro.Controller;

import com.example.pemro.Repository.PostinganRepository;
import com.example.pemro.model.Offer;
import com.example.pemro.model.Postingan;
import com.example.pemro.model.Request;
import com.example.pemro.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class PostDetailController {

    @FXML private Label titleLabel, typeLabel, statusLabel, detail1Label, detail2Label;
    @FXML private TextArea descArea;
    @FXML private Button deleteButton, editButton;

    private Postingan currentPost;
    private User loggedInUser;
    private PostinganRepository postRepo = new PostinganRepository();

    public void setPostData(Postingan post, User user) {
        this.currentPost = post;
        this.loggedInUser = user;

        titleLabel.setText(post.getTitle());
        descArea.setText(post.getDescription());
        typeLabel.setText("[" + post.getPostType() + "]");
        statusLabel.setText(post.getStatus());

        if (post instanceof Offer) {
            Offer offer = (Offer) post;
            detail1Label.setText("Pengalaman: " + offer.getExperienceYears() + " Tahun");
            detail2Label.setText("Portfolio: " + offer.getPortfolioLink());
        } else if (post instanceof Request) {
            Request req = (Request) post;
            detail1Label.setText("Deadline: " + req.getDeadline());
            detail2Label.setText("Urgensi: " + req.getUrgencyLevel());
        }

        // Hanya pemilik post yang bisa edit/delete
        boolean isOwner = user.getId() == post.getUserId();
        deleteButton.setVisible(isOwner);
        editButton.setVisible(isOwner);
    }

    @FXML
    private void handleEdit() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditPost.fxml"));
            Scene scene = new Scene(loader.load());

            EditPostController controller = loader.getController();
            controller.setPostData(currentPost);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Edit Postingan");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            // Refresh detail setelah edit
            setPostData(currentPost, loggedInUser);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDelete() {
        if (postRepo.deletePost(currentPost.getId())) {
            showAlert("Sukses", "Postingan berhasil dihapus.");
            handleClose();
        } else {
            showAlert("Gagal", "Gagal menghapus postingan.");
        }
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) titleLabel.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
