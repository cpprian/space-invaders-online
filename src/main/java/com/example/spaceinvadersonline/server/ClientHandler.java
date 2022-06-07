package com.example.spaceinvadersonline.server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.gson.Gson;

public class ClientHandler implements Runnable {
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private DataInputStream in;
    private DataOutputStream out;
    private boolean canConnect = false;


    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            canConnect = true;
            if (clientHandlers.size() < 2) {
                clientHandlers.add(this);
                logger.log(Level.INFO, "SUCCESSFUL CONNECTION");
            }
            if (clientHandlers.size() == 2) {
                int counterID = 0;
                for (ClientHandler clientHandler : clientHandlers) {
                    clientHandler.out.writeUTF("OK");
                    clientHandler.out.flush();
                    clientHandler.out.writeUTF(String.valueOf(counterID++));
                    out.flush();
                }
            }
        } catch (IOException err) {
            logger.log(Level.WARNING, "Something went wrong: " + err);
        }
    }

    public boolean getConnection() {
        return canConnect;
    }

    @Override
    public void run() {
        while(true) {
            try {
                String readMessage = in.readUTF();
                System.out.println(readMessage);
                for (ClientHandler clientHandler : clientHandlers) {
                    if (clientHandler != this) {
                        clientHandler.out.writeUTF(readMessage);
                        clientHandler.out.flush();
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
