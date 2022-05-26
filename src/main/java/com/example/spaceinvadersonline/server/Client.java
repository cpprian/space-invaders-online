package com.example.spaceinvadersonline.server;

import javafx.fxml.Initializable;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client implements Initializable {
    private Socket socket = new Socket("localhost", 8080);
    private DataOutputStream out;
    private String clientName;
    private Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public Client(String clientName) throws IOException {
        this.clientName = clientName;
        out = new DataOutputStream(socket.getOutputStream());
        out.writeUTF(clientName);
        out.flush();
    }

    // out.close();
    public void listen() {
        Thread thread = new Thread(
                () -> {
//                        while (socket.isConnected()) {
//
//                        }
                }
        );
        thread.start();
    }

    public void close() {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch(IOException err) {
            logger.log(Level.WARNING, "Something went wrong: " + err);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // run game
    }
}
