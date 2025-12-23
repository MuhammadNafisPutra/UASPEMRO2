package com.example.pemro.Controller;

import com.example.pemro.Repository.UserRepository;
import com.example.pemro.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    private UserRepository userRepository = new UserRepository();

    @FXML
    void handleLogin(ActionEvent event) {
        User user = userRepository.loginUser(usernameField.getText(), passwordField.getText());

        if (user != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pemro/view/Dashboard.fxml"));
                Parent root = loader.load();

                DashboardController dashboardController = loader.getController();
                dashboardController.setUserInfo(user);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setMaximized(true);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Username/Password Salah!").showAndWait();
        }
    }

    @FXML
    void handleGoToRegister(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pemro/view/Register.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.setMaximized(true);
            stage.setTitle("BarterSkill - Register");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}