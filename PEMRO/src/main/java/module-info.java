module com.example.pemro {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; // Penting untuk koneksi database

    // Mengizinkan JavaFX mengakses view dan controller
    opens com.example.pemro.view to javafx.fxml;
    opens com.example.pemro.Controller to javafx.fxml;

    exports com.example.pemro;
}