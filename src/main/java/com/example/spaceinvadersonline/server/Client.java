package com.example.spaceinvadersonline.server;

import javafx.application.Application;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    private Socket socket = new Socket("localhost", 8080);
    private DataOutputStream out;
    private DataInputStream in;
    private String clientName;
    private boolean isReady = false;
    private Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public Client(String clientName) throws IOException {
        this.clientName = clientName;
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());
        out.writeUTF(clientName);
        out.flush();
    }

    // out.close();
    public void listen() {
        Thread thread = new Thread(
                () -> {
                    while (socket.isConnected()) {
                        try {
                            String message = in.readUTF();
                            if (message.equals("OK")) {
                                logger.log(Level.INFO, "START GAME");
                                isReady = true;
                                break;
                            }
                            logger.log(Level.WARNING, "Can't read message");
                        } catch (IOException err) {
                            logger.log(Level.WARNING, "Can't read message");
                        }
                    }
                    System.out.println(socket.isConnected());
                }
        );
        thread.start();
    }

    public boolean getIsReady() {
        return isReady;
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
}
