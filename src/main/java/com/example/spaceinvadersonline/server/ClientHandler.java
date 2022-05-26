package com.example.spaceinvadersonline.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler implements Runnable {
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private DataInputStream in;
    public boolean canConnect = true;


    // create new Player object
    // activate client listener
    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            in = new DataInputStream(socket.getInputStream());
            logger.log(Level.INFO, in.readUTF());
            if (clientHandlers.size() < 2) {
                clientHandlers.add(this);
                logger.log(Level.INFO, "SUCCESSFUL CONNECTION");
            } else {
                canConnect = false;
            }
        } catch (IOException err) {
            logger.log(Level.WARNING, "Something went wrong: " + err);
        }
    }

    @Override
    public void run() {
        // run game
    }

    public void close(Socket socket) {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch(IOException err) {
            logger.log(Level.WARNING, "Something went wrong: " + err);
        }
    }
}
