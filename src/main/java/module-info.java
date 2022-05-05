module com.example.spaceinvadersonline {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.spaceinvadersonline to javafx.fxml;
    exports com.example.spaceinvadersonline;
}