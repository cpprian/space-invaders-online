package com.example.spaceinvadersonline;

import com.example.spaceinvadersonline.server.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Enter {
    @FXML
    private TextField nickname; // get Text
    @FXML
    private Button enterButton; // get Button

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
                Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, "Client connected");
            } catch (IOException err) {
                throw new RuntimeException(err);
            }
        });
    }
}
