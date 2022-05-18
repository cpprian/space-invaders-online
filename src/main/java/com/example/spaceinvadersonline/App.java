package com.example.spaceinvadersonline;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App extends Application {
    private Stage stage;

    @FXML
    protected void onStartButtonClick(ActionEvent event) throws IOException {
        Parent page = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("game.fxml")),
                null, new JavaFXBuilderFactory());
        Scene scene = new Scene(page, 1440, 900);
        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        this.stage.setScene(scene);
        this.stage.show();
    }

    @FXML
    protected void onOptionsButtonClick(ActionEvent event) throws IOException {
        Parent page = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("options.fxml")),
                null, new JavaFXBuilderFactory());
        Scene scene = new Scene(page, 1440, 900);
        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        this.stage.setScene(scene);
        this.stage.show();
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            this.stage = primaryStage;
            Parent page = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("menu.fxml")));
            Scene scene = new Scene(page, 1440, 900);
            this.stage.setScene(scene);
            this.stage.show();
        } catch (Exception e) {
            logError(e);
        }
    }

    private void logError(Exception ex) {
        Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
    }

    public static void main(String[] args) {
        launch();
    }
}