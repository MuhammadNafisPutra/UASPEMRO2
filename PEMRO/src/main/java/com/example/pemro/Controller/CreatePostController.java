package com.example.pemro.Controller;

import com.example.pemro.Repository.PostinganRepository;
import com.example.pemro.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.sql.Date;
import java.time.LocalDate;
import java.io.IOException;

public class CreatePostController {
    @FXML private TextField titleField, experienceField, portfolioField;
    @FXML private TextArea descArea;
    @FXML private RadioButton radioOffer, radioRequest;
    @FXML private VBox offerBox, requestBox;
    @FXML private DatePicker deadlinePicker;
    @FXML private ComboBox<String> urgencyCombo;

    private User currentUser;
    private PostinganRepository postRepo = new PostinganRepository();
    private ToggleGroup typeGroup;

    @FXML
    public void initialize() {
        typeGroup = new ToggleGroup();
        radioOffer.setToggleGroup(typeGroup);
        radioRequest.setToggleGroup(typeGroup);
        urgencyCombo.getItems().addAll("Low", "Medium", "High");
    }

    public void setUser(User user) {
        this.currentUser = user;
    }

    @FXML
    private void handleTypeChange(ActionEvent event) {
        offerBox.setVisible(radioOffer.isSelected());
        requestBox.setVisible(radioRequest.isSelected());
    }

    @FXML
    private void handleSave(ActionEvent event) {
        String title = titleField.getText();
        String desc = descArea.getText();

        if (title.isEmpty() || desc.isEmpty()) {
            showAlert("Error", "Judul dan Deskripsi wajib diisi!");
            return;
        }

        Postingan newPost;
        if (radioOffer.isSelected()) {
            try {
                int exp = Integer.parseInt(experienceField.getText());
                newPost = new Offer(currentUser.getUserId(), title, desc, exp, portfolioField.getText());
            } catch (NumberFormatException e) {
                showAlert("Error", "Pengalaman harus angka!");
                return;
            }
        } else if (radioRequest.isSelected()) {
            LocalDate localDate = deadlinePicker.getValue();
            String urgency = urgencyCombo.getValue();
            if (localDate == null || urgency == null) {
                showAlert("Error", "Isi deadline & urgensi!");
                return;
            }
            newPost = new Request(currentUser.getUserId(), title, desc, Date.valueOf(localDate), urgency);
        } else {
            showAlert("Error", "Pilih tipe postingan!");
            return;
        }

        if (postRepo.savePost(newPost)) {
            showAlert("Sukses", "Postingan berhasil diterbitkan!");
            backToDashboard(event);
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
            showAlert("Error", "Gagal memuat Dashboard: " + e.getMessage());
        }
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}