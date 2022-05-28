package com.example.spaceinvadersonline;

import com.example.spaceinvadersonline.server.Client;
import com.example.spaceinvadersonline.server.ClientHandler;
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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Enter {
    @FXML
    private TextField nickname; // get Text
    @FXML
    private Button enterButton;

    public void enterGame(ActionEvent event) {
        // run Client
        enterButton.setOnMouseClicked(e -> {
            String name = nickname.getText();
            if (name.length() > 10) {
                name = name.substring(0, 10);
            }
            try {
                Client client = new Client(name);
                client.listen();
                while (true) {
                    if (client.getIsReady()) {
                        System.out.println("START GAME");
                        break;
                    }
                    System.out.print(client.getIsReady()+"\t");
                }
                // start game
                Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, "Client connected");
                initGame(event);
            } catch (Exception err) {
                throw new RuntimeException(err);
            }
        });
    }

    @FXML
    protected void initGame(ActionEvent event) throws Exception {
        Parent page = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("tempgame.fxml")),
                null, new JavaFXBuilderFactory());
        Scene scene = new Scene(page,1700, 1010);
        scene.setFill(Color.BLACK);
        Stage gameStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        gameStage.setScene(scene);
        gameStage.show();
        gameStage.centerOnScreen();
    }
}
