package com.example.spaceinvadersonline.server;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    private Socket socket;
    private String clientName;
    private Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public Client(Socket socket, String clientName) {
        this.socket = socket;
        this.clientName = clientName;
    }

    public void listen() {
        Thread thread = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
//                        while (socket.isConnected()) {
//
//                        }
                    }
                }
        );
        thread.start();
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

    public static void main() throws IOException {
        Socket socket = new Socket("localhost", 8080);
        String text = "Hello";
        Client client = new Client(socket, text);
        client.listen();
    }
}
