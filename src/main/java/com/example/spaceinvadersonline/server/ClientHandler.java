package com.example.spaceinvadersonline.server;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
    public JSONArray jsonArray = new JSONArray();
    private DataOutputStream out;
    public boolean canConnect = true;
    private boolean isReady = false;
    public String clientName;
    public static ArrayList<DataPackage> dataPackage = new ArrayList<>();
    private static int whichPlayer = 0;


    // create new Player object
    // activate client listener
    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            clientName = in.readUTF();
            if (clientHandlers.size() < 2) {
                clientHandlers.add(this);
                logger.log(Level.INFO, "SUCCESSFUL CONNECTION");
            } else {
                canConnect = false;
            }
            if (clientHandlers.size() == 2) {
                for (ClientHandler clientHandler : clientHandlers) {
                    clientHandler.out.writeUTF("OK");
                    out.flush();
                    clientHandler.isReady = true;

                }
                dataPackage.add(new DataPackage(clientHandlers.get(0).clientName,whichPlayer,0,3, whichPlayer++==0?50:1200));
                dataPackage.add(new DataPackage(clientHandlers.get(1).clientName,whichPlayer,0,3, whichPlayer++==0?50:1200));

                for (int i = 0; i < 2; i++) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("name", dataPackage.get(i).playerName);
                    jsonObject.put("x", dataPackage.get(i).playerX);
                    jsonObject.put("lives", dataPackage.get(i).playerLives);
                    jsonObject.put("score", dataPackage.get(i).playerPoints);
                    jsonObject.put("id", dataPackage.get(i).playerID);
                    jsonArray.add(jsonObject);
                }
                System.out.println(jsonArray);
            }
        } catch (IOException err) {
            logger.log(Level.WARNING, "Something went wrong: " + err);
        }
    }

    @Override
    public void run() {
        // run game
        while (socket.isConnected()) {
            if (isReady) {
                for (ClientHandler clientHandler : clientHandlers) {
                    try {
                        clientHandler.out.writeUTF(jsonArray.toJSONString());
                        clientHandler.out.flush();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                break;
            }
        }

        while (socket.isConnected()) {
            for (ClientHandler clientHandler: clientHandlers) {
                try {
                    String data = clientHandler.in.readUTF();
                    if (!data.equals("")) {
                        continue;
                    }
                    for (ClientHandler clientHandler1: clientHandlers) {
                        if (!clientHandler1.getClientName().equals(getClientName())) {
                            clientHandler1.out.writeUTF(data);
                            clientHandler1.out.flush();
                        }
                    }
                } catch(IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public String getClientName() {
        return clientName;
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
