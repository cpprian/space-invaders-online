package com.example.spaceinvadersonline;

import com.example.spaceinvadersonline.logic.Logic;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Game implements Initializable, Runnable {
    @FXML
    private Pane rootPane;
    AnimationTimer timer;

    private final String IP = "localhost";
    private final int port = 22222;
    private final Scanner scanner = new Scanner(System.in);
    private Thread thread;
    private ServerSocket serverSocket;
    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;

    boolean accepted = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Logic logic = new Logic();
        Rectangle background= new Rectangle(0, 0, 1700, 1080);
        background.setFill(Color.BLACK);
        Rectangle rectangle = new Rectangle(1300, 0, 400, 1010);
        rectangle.getStyleClass().add("rectangle-modify");
        rectangle.setFill(Color.BLACK);
        rectangle.setStroke(Color.WHITE);
        rectangle.setStrokeWidth(2);
        // display lives and score
        Text logo1= new Text("SPACE");
        logo1.setLayoutX(1370);
        logo1.setLayoutY(100);
        logo1.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 63));
        logo1.setFill(Color.WHITE);
        Text logo2= new Text("INVADERS");
        logo2.setLayoutX(1310);
        logo2.setLayoutY(160);
        logo2.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 63));
        logo2.setFill(Color.WHITE);
        Text player1 = new Text("Player 1");
        player1.setLayoutX(1350);
        player1.setLayoutY(300);
        player1.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 60));
        player1.setFill(Color.WHITE);
        Text lives = new Text("Lives: 3");
        lives.setLayoutX(1400);
        lives.setLayoutY(390);
        lives.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
        lives.setFill(Color.WHITE);
        Text points = new Text("Points: 0");
        points.setLayoutX(1400);
        points.setLayoutY(470);
        points.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
        points.setFill(Color.WHITE);
        Text player2 = new Text("Player 2");
        player2.setLayoutX(1350);
        player2.setLayoutY(600);
        player2.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 60));
        player2.setFill(Color.WHITE);
        Text lives2 = new Text("Lives: 3");
        lives2.setLayoutX(1400);
        lives2.setLayoutY(690);
        lives2.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
        lives2.setFill(Color.WHITE);
        Text points2 = new Text("Points: 0");
        points2.setLayoutX(1400);
        points2.setLayoutY(770);
        points2.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
        points2.setFill(Color.WHITE);
        rootPane.getChildren().addAll(background,rectangle,logo1,logo2,player1,player2,lives,lives2, points,points2);
//        rootPane.setBackground(new Background());

        //start moving of monsters
        logic.monster.dotR.setLayoutX(0);

        //create player
        rootPane.getChildren().add(logic.player.player);

        //create monsters
        logic.monster.addMonsters(rootPane);

        // create houses
        logic.house.addHouses(rootPane);

        timer = new AnimationTimer() {
            @Override
            public void handle(long arg0) {
                logic.gameUpdate(rootPane, timer, points, lives);
            }
        };
        timer.start();


        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            if(!logic.monster.monsters.isEmpty()) {
                logic.monster.monstersShoot(rootPane);
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        rootPane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

        // moving player
        rootPane.setFocusTraversable(true);
        rootPane.setOnKeyPressed(e-> {
            if(e.getCode() == KeyCode.RIGHT && logic.player.player.getLayoutX() != 1250) {
                logic.player.player.setLayoutX(logic.player.player.getLayoutX() + 10);
            }
            if(e.getCode() == KeyCode.LEFT && logic.player.player.getLayoutX() != 0) {
                logic.player.player.setLayoutX(logic.player.player.getLayoutX() - 10);
            }
            if(e.getCode() == KeyCode.SPACE) {
                logic.player.playerShoot(rootPane, logic.player.player.getLayoutX());
            }
        });

        if (!connect()) {
            initializeServer();
        }

        thread = new Thread((Runnable) this, "TicTacToe");
        thread.start();
    }

    private void listenForServerRequest() {
        Socket socket;
        try {
            socket = serverSocket.accept();
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            accepted = true;
            System.out.println("CLIENT HAS REQUESTED TO JOIN, AND WE HAVE ACCEPTED");
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private boolean connect() {
        try {
            socket = new Socket(IP, port);
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            accepted = true;
        } catch(IOException e) {
            System.out.println("Unable to connect to the adress: " + IP + ":" + port + " | Starting a server");
            return false;
        }
        System.out.println("Successfully connected to the server.");
        return true;
    }

    private void initializeServer() {
        try {
            serverSocket = new ServerSocket(port, 8, InetAddress.getByName(IP));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(true) {
            if (!accepted) {
                listenForServerRequest();
            }
        }
    }
}
