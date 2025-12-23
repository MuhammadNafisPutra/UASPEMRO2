package com.example.pemro.Controller;

import com.example.pemro.Repository.UserRepository;
import com.example.pemro.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

public class RegisterController {
    @FXML private TextField fullNameField, usernameField;
    @FXML private PasswordField passwordField;
    private UserRepository userRepo = new UserRepository();

    @FXML
    private void handleRegister() {
        if (fullNameField.getText().isEmpty() || usernameField.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Kolom tidak boleh kosong!").showAndWait();
            return;
        }
        User newUser = new User(usernameField.getText(), passwordField.getText(), fullNameField.getText());
        if (userRepo.registerUser(newUser)) {
            new Alert(Alert.AlertType.INFORMATION, "Sukses! Silakan Login.").showAndWait();
            handleBack();
        }
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pemro/view/Login.fxml"));
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.setMaximized(true);
        } catch (IOException e) { e.printStackTrace(); }
    }
}