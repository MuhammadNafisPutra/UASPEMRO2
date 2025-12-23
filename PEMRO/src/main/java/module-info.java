module com.example.pemro {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.pemro to javafx.fxml;
    exports com.example.pemro;

    opens com.example.pemro.Controller to javafx.fxml;
    exports com.example.pemro.Controller;
    opens com.example.pemro.model to javafx.base;
}