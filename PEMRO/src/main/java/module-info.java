module com.example.pemro {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.pemro to javafx.fxml;
    opens com.example.pemro.Controller to javafx.fxml;
    opens com.example.pemro.Repository to javafx.fxml;

    exports com.example.pemro;
}
