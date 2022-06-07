package com.example.spaceinvadersonline;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent page = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("menu.fxml")));
            Scene scene = new Scene(page, 1700, 1010);
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.centerOnScreen();
        } catch (Exception e) {
            logError(e);
        }
    }

    private void logError(Exception ex) {
        Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
    }

    public static void main(String[] args) {
        launch();
    }
}