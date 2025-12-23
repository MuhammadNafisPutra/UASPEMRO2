package com.example.pemro.Controller;

import com.example.pemro.model.*;
import com.example.pemro.Repository.PostinganRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.io.IOException;

public class EditPostController {
    @FXML private TextField titleField, experienceField, portfolioField;
    @FXML private TextArea descArea;
    @FXML private RadioButton radioOffer, radioRequest;
    @FXML private VBox offerBox, requestBox;
    @FXML private DatePicker deadlinePicker;
    @FXML private ComboBox<String> urgencyCombo;

    private Postingan post;
    private User currentUser;
    private PostinganRepository repo = new PostinganRepository();

    public void setUser(User user) {
        this.currentUser = user;
    }

    public void setPostData(Postingan post) {
        this.post = post;
        titleField.setText(post.getTitle());
        descArea.setText(post.getDescription());

        if (urgencyCombo.getItems().isEmpty()) urgencyCombo.getItems().addAll("Low", "Medium", "High");

        if (post instanceof Offer) {
            Offer o = (Offer) post;
            radioOffer.setSelected(true);
            offerBox.setVisible(true);
            requestBox.setVisible(false);
            experienceField.setText(String.valueOf(o.getExperienceYears()));
            portfolioField.setText(o.getPortfolioLink());
        } else if (post instanceof Request) {
            Request r = (Request) post;
            radioRequest.setSelected(true);
            requestBox.setVisible(true);
            offerBox.setVisible(false);
            if (r.getDeadline() != null) deadlinePicker.setValue(r.getDeadline().toLocalDate());
            urgencyCombo.setValue(r.getUrgencyLevel());
        }
    }

    @FXML
    private void handleUpdate(ActionEvent event) {
        try {
            post.setTitle(titleField.getText());
            post.setDescription(descArea.getText());

            if (post instanceof Offer) {
                Offer o = (Offer) post;
                o.setExperienceYears(Integer.parseInt(experienceField.getText()));
                o.setPortfolioLink(portfolioField.getText());
            } else if (post instanceof Request) {
                Request r = (Request) post;
                if (deadlinePicker.getValue() != null) r.setDeadline(java.sql.Date.valueOf(deadlinePicker.getValue()));
                r.setUrgencyLevel(urgencyCombo.getValue());
            }

            if (repo.updatePost(post)) {
                new Alert(Alert.AlertType.INFORMATION, "Postingan berhasil diperbarui!").showAndWait();
                backToDashboard(event);
            } else {
                new Alert(Alert.AlertType.ERROR, "Gagal update database.").showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        backToDashboard(event);
    }

    private void backToDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pemro/view/Dashboard.fxml"));
            Parent root = loader.load();

            DashboardController controller = loader.getController();
            controller.setUserInfo(currentUser);

            Scene currentScene = ((Node) event.getSource()).getScene();
            currentScene.setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}