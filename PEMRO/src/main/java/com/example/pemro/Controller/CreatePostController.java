package com.example.pemro.Controller;

import com.example.pemro.Repository.PostinganRepository;
import com.example.pemro.model.Offer;
import com.example.pemro.model.Postingan;
import com.example.pemro.model.Request;
import com.example.pemro.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.Date;
import java.time.LocalDate;

public class CreatePostController {

    @FXML private TextField titleField;
    @FXML private TextArea descArea;
    @FXML private RadioButton radioOffer, radioRequest;

    @FXML private VBox offerBox;
    @FXML private TextField experienceField, portfolioField;

    @FXML private VBox requestBox;
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

    @FXML
    private void handleTypeChange() {
        if (radioOffer.isSelected()) {
            offerBox.setVisible(true);
            requestBox.setVisible(false);
        } else if (radioRequest.isSelected()) {
            offerBox.setVisible(false);
            requestBox.setVisible(true);
        }
    }

    public void setUser(User user) { this.currentUser = user; }

    @FXML
    private void handleSave() {
        String title = titleField.getText();
        String desc = descArea.getText();
        if (title.isEmpty() || desc.isEmpty()) { showAlert("Error", "Judul dan Deskripsi wajib diisi!"); return; }

        Postingan newPost;
        if (radioOffer.isSelected()) {
            try {
                int exp = Integer.parseInt(experienceField.getText());
                newPost = new Offer(currentUser.getId(), title, desc, exp, portfolioField.getText());
            } catch (NumberFormatException e) { showAlert("Error", "Pengalaman harus berupa angka!"); return; }
        } else if (radioRequest.isSelected()) {
            LocalDate localDate = deadlinePicker.getValue();
            String urgency = urgencyCombo.getValue();
            if (localDate == null || urgency == null) { showAlert("Error", "Deadline dan Urgensi wajib diisi!"); return; }
            newPost = new Request(currentUser.getId(), title, desc, Date.valueOf(localDate), urgency);
        } else { showAlert("Error", "Pilih tipe postingan dulu!"); return; }

        if (postRepo.savePost(newPost)) { showAlert("Sukses", "Postingan berhasil diterbitkan!"); closeWindow(); }
        else showAlert("Gagal", "Terjadi kesalahan database.");
    }

    @FXML
    private void handleCancel() { closeWindow(); }

    private void closeWindow() {
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
