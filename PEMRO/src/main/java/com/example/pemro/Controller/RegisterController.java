package com.example.pemro.Controller;

import com.example.pemro.Repository.UserRepository;
import com.example.pemro.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class RegisterController {

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private UserRepository userRepo = new UserRepository();

    @FXML
    private void handleRegister() {
        String nama = fullNameField.getText();
        String user = usernameField.getText();
        String pass = passwordField.getText();

        if (nama.isEmpty() || user.isEmpty() || pass.isEmpty()) {
            showAlert("Error", "Semua kolom wajib diisi!");
            return;
        }

        User newUser = new User(user, pass, nama);

        if (userRepo.registerUser(newUser)) {  // pastikan 'r' kecil
            showAlert("Sukses", "Akun berhasil dibuat! Silakan Login.");
            handleBack(); // otomatis kembali ke login
        } else {
            showAlert("Gagal", "Terjadi kesalahan saat menyimpan data.");
        }
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/pemro/view/Login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
