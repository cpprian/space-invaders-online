package com.example.spaceinvadersonline;

import com.example.spaceinvadersonline.data.DataPackage;
import com.example.spaceinvadersonline.logic.Logic;
import com.google.gson.JsonArray;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TempGame implements Initializable {
    @FXML
    private Pane rootPane;
    private Socket socket;
    public DataOutputStream out;
    public DataInputStream in;
    private Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private AnimationTimer timer;
    private DataPackage currentPlayer;
    private DataPackage secondPlayer;
    private Logic logic;
    private boolean canConnect;

    private Text lives1;
    private Text lives2;
    private Text points1;
    private Text points2;
    private Gson gson = new Gson();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initSocket();
        initLayout(Enter.getCurrentPlayerName());
    }

    private void initSocket() {
        try {
            socket = new Socket("localhost", 8080);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
        } catch (IOException err) {
            logger.log(Level.WARNING, "Can't init socket");
        }
    }
    public void listenForServerRequest() {
        if (!socket.isConnected()) {
            logger.log(Level.INFO, "DISCONNECTED");
            canConnect = false;
            return;
        }

        while (socket.isConnected()) {
            try {
                String message = in.readUTF();
                if (message.equals("OK")) {
                    logger.log(Level.INFO, "START GAME");
                    canConnect = true;
                    break;
                }
                System.out.println(message);
            } catch (IOException err) {
                logger.log(Level.WARNING, "Can't read message");
            }
        }
    }
    private void initLayout(String nameCurrentPlayer) {
        rootPane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        rootPane.setStyle("-fx-background-color: black;");

        // wait for other player
        listenForServerRequest();
        if (!canConnect) {
            logger.log(Level.WARNING, "Can't connect to server");
            return;
        }

        // new player
        int playerID;
        try {
            playerID = Integer.parseInt(in.readUTF());
        }catch(IOException err) {
            logger.log(Level.WARNING, "Can't read playerID");
            return;
        }
        int xposition = playerID == 0? 50: 1100;
        currentPlayer = new DataPackage(nameCurrentPlayer, playerID, xposition);
        secondPlayer = new DataPackage();

        // read second player & send current player
        Thread thread = new Thread(() -> {
            while(true) {
                try {
                    // send
                    JSONArray jsonArray = new JSONArray();
                    Iterator<Integer> shoots = getCurrentPlayer().getShoots().iterator();
                    while (shoots.hasNext()) {
                        jsonArray.add(shoots.next());
                    }
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("name", getCurrentPlayer().getName());
                    jsonObject.put("id", getCurrentPlayer().getId());
                    jsonObject.put("xPosition", getCurrentPlayer().getxPosition());
                    jsonObject.put("lives", getCurrentPlayer().getLives());
                    jsonObject.put("points", getCurrentPlayer().getPoints());
                    jsonObject.put("isWin", getCurrentPlayer().isWin());
                    jsonObject.put("shoots", jsonArray);
                    System.out.println("shoots --> " + jsonArray);
                    out.writeUTF(jsonObject.toJSONString());
                    out.flush();

                    // read
                    String readMessage = in.readUTF();
                    JSONParser jsonParser = new JSONParser();
                    JSONObject readJson = (JSONObject)jsonParser.parse(readMessage);
                    secondPlayer.setName((String) readJson.get("name"));
                    secondPlayer.setId(Math.toIntExact((Long) readJson.get("id")));
                    secondPlayer.setxPosition(Math.toIntExact((Long) readJson.get("xPosition")));
                    secondPlayer.setLives(Math.toIntExact((Long) readJson.get("lives")));
                    secondPlayer.setPoints(Math.toIntExact((Long) readJson.get("points")));
                    secondPlayer.setWin((Boolean) readJson.get("isWin"));
                    secondPlayer.setShootsInteger((ArrayList<Long>) readJson.get("shoots"));
                    setSecondPlayer(secondPlayer);
                } catch (IOException err) {
                    logger.log(Level.WARNING, "Something went wrong: " + err.getMessage());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();

        // wait for secondPlayer
        while (true) {
//            System.out.print("not ready\t");
            if (secondPlayer != null) {
//                System.out.println("\nready");
                break;
            }
        }

        logic = new Logic(getCurrentPlayer(), getSecondPlayer());
        makeLogoPane();
        makePlayerNamePane(getCurrentPlayer().getName(), getSecondPlayer().getName());
        setPlayerInfoPane();

        //start moving of monsters
        logic.monster.dotR.setLayoutX(0);

        //create players
        rootPane.getChildren().addAll(logic.player1.player, logic.player2.player);

        //create monsters
        logic.monster.addMonsters(rootPane);

        // create houses
        logic.house.addHouses(rootPane);

        timer = new AnimationTimer() {
            @Override
            public void handle(long arg0) {
                logic.updatePlayer(rootPane, getSecondPlayer());
                logic.gameUpdate(rootPane, timer, points1, lives1, points2, lives2, getCurrentPlayer(), getSecondPlayer());
            }
        };
        timer.start();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if(!logic.monster.monsters.isEmpty()) {
                logic.monster.monstersShoot(rootPane);
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        rootPane.setFocusTraversable(true);
        rootPane.setOnKeyPressed(e-> {
            if (currentPlayer.getId() == 0) {
                if(e.getCode() == KeyCode.RIGHT && logic.player1.player.getLayoutX() != 1250) {
                    logic.player1.player.setLayoutX(logic.player1.player.getLayoutX() + 50);
                    currentPlayer.setxPosition((int) logic.player1.player.getLayoutX());
                }
                if(e.getCode() == KeyCode.LEFT && logic.player1.player.getLayoutX() != 0) {
                    logic.player1.player.setLayoutX(logic.player1.player.getLayoutX() - 50);
                    currentPlayer.setxPosition((int) logic.player1.player.getLayoutX());
                }
                if(e.getCode() == KeyCode.SPACE) {
                    if (logic.player1.getShoots().size() < 10) {
                        logic.player1.playerShoot(rootPane, logic.player1.player.getLayoutX());
                        currentPlayer.setShoots(logic.player2.getShoots());
                    } else {
                        logic.player1.playersShootSwap(rootPane, logic.player1.player.getLayoutX());
                        currentPlayer.setShoots(logic.player2.getShoots());
                    }
                }
            } else {
                if(e.getCode() == KeyCode.RIGHT && logic.player2.player.getLayoutX() != 1250) {
                    logic.player2.player.setLayoutX(logic.player2.player.getLayoutX() + 50);
                    currentPlayer.setxPosition((int) logic.player2.player.getLayoutX());
                }
                if(e.getCode() == KeyCode.LEFT && logic.player2.player.getLayoutX() != 0) {
                    logic.player2.player.setLayoutX(logic.player2.player.getLayoutX() - 50);
                    currentPlayer.setxPosition((int) logic.player2.player.getLayoutX());
                }
                if(e.getCode() == KeyCode.SPACE) {
                    if (logic.player2.getShoots().size() < 10) {
                        logic.player2.playerShoot(rootPane, logic.player2.player.getLayoutX());
                        currentPlayer.setShoots(logic.player2.getShoots());
                    } else {
                        logic.player2.playersShootSwap(rootPane, logic.player2.player.getLayoutX());
                        currentPlayer.setShoots(logic.player2.getShoots());
                    }

                }
            }
        });
    }
    private void makeLogoPane() {
        // right pane
        Rectangle rect = new Rectangle(1300, 0, 400, 1010);
        rect.setFill(Color.BLACK);
        rect.setStroke(Color.WHITE);
        rect.setStrokeWidth(2);

        // logo
        Text space = new Text("SPACE");
        space.setLayoutX(1370);
        space.setLayoutY(100);
        space.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 63));
        space.setFill(Color.WHITE);
        Text invaders = new Text("INVADERS");
        invaders.setLayoutX(1310);
        invaders.setLayoutY(160);
        invaders.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 63));
        invaders.setFill(Color.WHITE);

        rootPane.getChildren().addAll(rect, space, invaders);
    }
    private void makePlayerNamePane(String p1, String p2) {
        Text p1Name = new Text(p1);
        p1Name.setLayoutX(1350);
        p1Name.setLayoutY(300);
        p1Name.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        p1Name.setFill(Color.WHITE);

        Text p2Name = new Text(p2);
        p2Name.setLayoutX(1350);
        p2Name.setLayoutY(600);
        p2Name.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        p2Name.setFill(Color.WHITE);
        rootPane.getChildren().addAll(p1Name,p2Name);
    }
    private void setPlayerInfoPane() {
        lives1 = new Text("Lives: " + 3);
        lives1.setLayoutX(1350);
        lives1.setLayoutY(400);
        lives1.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        lives1.setFill(Color.WHITE);

        lives2 = new Text("Lives: " + 3);
        lives2.setLayoutX(1350);
        lives2.setLayoutY(700);
        lives2.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        lives2.setFill(Color.WHITE);

        points1 = new Text("Points: " + 0);
        points1.setLayoutX(1350);
        points1.setLayoutY(500);
        points1.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        points1.setFill(Color.WHITE);

        points2 = new Text("Points: " + 0);
        points2.setLayoutX(1350);
        points2.setLayoutY(800);
        points2.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        points2.setFill(Color.WHITE);
        rootPane.getChildren().addAll(lives1,lives2,points1,points2);
    }
    private synchronized void setCurrentPlayer(DataPackage data) {
        currentPlayer = data;
    }
    private synchronized DataPackage getCurrentPlayer() {
        return currentPlayer;
    }
    private synchronized void setSecondPlayer(DataPackage secondPlayer) {
        this.secondPlayer = secondPlayer;
    }
    private synchronized DataPackage getSecondPlayer() {
        return secondPlayer;
    }
}
