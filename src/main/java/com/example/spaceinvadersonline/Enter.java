package com.example.spaceinvadersonline;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Enter {
    @FXML
    private TextField nickname;
    @FXML
    private Button enterButton;
    private static String playerName;

    public void enterGame(ActionEvent event) {
        enterButton.setOnMouseClicked(e -> {
            String name = nickname.getText();
            if (name.length() > 10) {
                name = name.substring(0, 10);
            }
            playerName = name;
            try {
                Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, "Client connected, initializing game");
                initGame(event);
            } catch (Exception err) {
                throw new RuntimeException(err);
            }
        });
    }

    public static String getCurrentPlayerName() {
        return playerName;
    }

    @FXML
    protected void initGame(ActionEvent event) throws Exception {
        Parent page = FXMLLoader.load(Objects.requireNonNull(Menu.class.getResource("tempgame.fxml")),
                null, new JavaFXBuilderFactory());
        Scene scene = new Scene(page,1700, 1010);
        scene.setFill(Color.BLACK);
        Stage gameStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        gameStage.setScene(scene);
        gameStage.show();
        gameStage.centerOnScreen();
    }
}
