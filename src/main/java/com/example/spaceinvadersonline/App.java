package com.example.spaceinvadersonline;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App extends Application {
    private Stage stage = new Stage();

    @FXML
    protected void onStartButtonClick(ActionEvent event) throws Exception {
        Parent page = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("enter.fxml")),
                null, new JavaFXBuilderFactory());
        Scene scene = new Scene(page,1700, 1010);
        scene.setFill(Color.BLACK);
        //scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("thelook.css")).toExternalForm());
        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        this.stage.setScene(scene);
        this.stage.show();
        this.stage.centerOnScreen();
    }

//    @FXML
//    protected void onOptionsButtonClick(ActionEvent event) throws IOException {
//        Parent page = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("options.fxml")),
//                null, new JavaFXBuilderFactory());
//        Scene scene = new Scene(page);
//        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        this.stage.setFullScreen(true);
//        this.stage.setScene(scene);
//        this.stage.show();
//    }

    @Override
    public void start(Stage primaryStage) {
        try {
            this.stage = primaryStage;
            Parent page = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("menu.fxml")));
            Scene scene = new Scene(page, 1700, 1010);
            this.stage.setScene(scene);
            this.stage.show();
            this.stage.centerOnScreen();
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