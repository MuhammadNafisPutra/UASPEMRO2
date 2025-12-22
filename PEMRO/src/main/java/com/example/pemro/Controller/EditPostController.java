package com.example.pemro.Controller;

import com.example.pemro.model.Postingan;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditPostController {

    @FXML private TextField titleField;
    @FXML private TextArea descArea;

    private Postingan post;

    public void setPostData(Postingan post) {
        this.post = post;
        titleField.setText(post.getTitle());
        descArea.setText(post.getDescription());
    }

    @FXML
    private void handleSave() {
        // update postingan
        post.setTitle(titleField.getText());
        post.setDescription(descArea.getText());

        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }
}
