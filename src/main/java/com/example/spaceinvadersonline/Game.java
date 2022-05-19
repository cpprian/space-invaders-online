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
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class Game implements Initializable {
    @FXML
    Pane rootPane;
    Scene scene;

    AnimationTimer timer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Logic logic = new Logic();

        // display lives and score
        Text lives = new Text("Lives: 3");
        lives.setLayoutX(20);
        lives.setLayoutY(30);
        lives.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        lives.setFill(Color.WHITE);
        Text points = new Text("Points: 0");
        points.setLayoutX(350);
        points.setLayoutY(30);
        points.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        points.setFill(Color.WHITE);
        rootPane.getChildren().addAll(lives, points);
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
        rootPane.setOnKeyPressed(e-> {
            if(e.getCode() == KeyCode.RIGHT && logic.player.player.getLayoutX() != 1250) {
                logic.player.player.setLayoutX(logic.player.player.getLayoutX() + 5);
            }
            if(e.getCode() == KeyCode.LEFT && logic.player.player.getLayoutX() != 0) {
                logic.player.player.setLayoutX(logic.player.player.getLayoutX() - 5);
            }
            if(e.getCode() == KeyCode.SPACE) {
                logic.player.playerShoot(rootPane, logic.player.player.getLayoutX());
            }
        });
    }
}
