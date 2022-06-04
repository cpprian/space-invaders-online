package com.example.spaceinvadersonline.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private final ServerSocket serverSocket;
    private final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void launch() {
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket);
                if (clientHandler.getConnection()) {
                    Thread thread = new Thread(clientHandler);
                    thread.start();
                    logger.log(Level.INFO, "ACCEPT USER REQUEST");
                } else {
                    logger.log(Level.WARNING, "USER REQUEST DENIED");
                }
            }
        } catch (IOException err) {
            logger.log(Level.WARNING, "Server socket issue");
            close();
        }
    }

    public void close() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch(IOException err) {
            logger.log(Level.WARNING, "Can't close server");
        }
    }

    public static void main(String []args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            Server server = new Server(serverSocket);
            server.launch();
            server.close();
        } catch (IOException err) {
            err.printStackTrace();
        }
    }
}
