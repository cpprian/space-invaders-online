module com.example.spaceinvadersonline {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.logging;
    requires json.simple;
    requires com.google.gson;


    opens com.example.spaceinvadersonline to javafx.fxml, com.google.gson;
    opens com.example.spaceinvadersonline.data to com.google.gson;
    exports com.example.spaceinvadersonline;
}