package com.example.spaceinvadersonline;

import com.example.spaceinvadersonline.logic.Logic;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Game extends Application {
    Pane root = new Pane();

    AnimationTimer timer;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
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
        root.getChildren().addAll(lives, points);

        //start moving of monsters
        logic.monster.dotR.setLayoutX(0);

        //create player
        root.getChildren().add(logic.player.player);

        //create monsters
        logic.monster.addMonsters(root);

        // create houses
        logic.house.addHouses(root);

        timer = new AnimationTimer() {
            @Override
            public void handle(long arg0) {
                logic.gameUpdate(root, timer, points, lives);
            }
        };
        timer.start();


        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            if(!logic.monster.monsters.isEmpty()) {
                logic.monster.monstersShoot(root);
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        Scene scene = new Scene(root, 1440, 900);
        scene.setFill(Color.NAVY); // background

        // moving player
        scene.setOnKeyPressed(e-> {
            if(e.getCode() == KeyCode.RIGHT && logic.player.player.getLayoutX() != 1250) {
                logic.player.player.setLayoutX(logic.player.player.getLayoutX() + 5);
            }
            if(e.getCode() == KeyCode.LEFT && logic.player.player.getLayoutX() != 0) {
                logic.player.player.setLayoutX(logic.player.player.getLayoutX() - 5);
            }
            if(e.getCode() == KeyCode.SPACE) {
                logic.player.playerShoot(root, logic.player.player.getLayoutX());
            }
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("Space Invaders");
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }
}
