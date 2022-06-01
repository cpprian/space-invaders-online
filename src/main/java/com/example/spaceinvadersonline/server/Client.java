package com.example.spaceinvadersonline.server;

import javafx.application.Application;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.example.spaceinvadersonline.server.ClientHandler.clientHandlers;
import static com.example.spaceinvadersonline.server.ClientHandler.dataPackage;

public class Client {
    private Socket socket = new Socket("localhost", 8080);
    private DataOutputStream out;
    public DataInputStream in;
    public static String clientName;
    private boolean isReady = false;
    private Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public static ArrayList<DataPackage> Players = new ArrayList<>();
    public JSONArray jsonArray = new JSONArray();

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

                    try {
                        String message = in.readUTF();
                        JSONParser jsonParser = new JSONParser();
                        Object object = jsonParser.parse(message);
                        JSONArray jsonArray = (JSONArray) object;
                        for (Object o : jsonArray) {
                            JSONObject jsonObject = (JSONObject) o;
                            String playerName = (String) jsonObject.get("name");
                            Long playerX = (Long) jsonObject.get("x");
                            Long playerPoints = (Long) jsonObject.get("score");
                            Long playerLives = (Long) jsonObject.get("lives");
                            Long playerID = (Long) jsonObject.get("id");
                            Players.add(new DataPackage(playerName,
                                    Math.toIntExact(playerID),
                                    Math.toIntExact(playerPoints),
                                    Math.toIntExact(playerLives),
                                    Math.toIntExact(playerX)));
                        }
                    } catch (IOException err) {
                        logger.log(Level.WARNING, "Can't read message");
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                    while (socket.isConnected()) {
//                        dataPackage.get(dataPackage.get(0).equals(clientName) ? 0: 1) = 0;

                        for (int i = 0; i < 2; i++) {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("name", dataPackage.get(i).playerName);
                            jsonObject.put("x", dataPackage.get(i).playerX);
                            jsonObject.put("lives", dataPackage.get(i).playerLives);
                            jsonObject.put("score", dataPackage.get(i).playerPoints);
                            jsonObject.put("id", dataPackage.get(i).playerID);
                            jsonArray.add(jsonObject);
                        }
                    }

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
