package com.example.spaceinvadersonline.server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler implements Runnable {
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private DataInputStream in;
    private DataOutputStream out;
    public boolean canConnect = true;


    // create new Player object
    // activate client listener
    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            logger.log(Level.INFO, in.readUTF());
            if (clientHandlers.size() < 2) {
                clientHandlers.add(this);
                logger.log(Level.INFO, "SUCCESSFUL CONNECTION");
            } else {
                canConnect = false;
            }
            if (clientHandlers.size() == 2) {
                for (ClientHandler clientHandler : clientHandlers) {
                    clientHandler.out.writeUTF("OK");
                }
                logger.log(Level.INFO, "HURRRY UP");
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
