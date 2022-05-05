package com.example.spaceinvadersonline.logic;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class Player {
    List<Circle> shoots = new ArrayList<>();
    public ImageView player;
    int numLives = 3;

    public Player() {
        player = player();
    }

    public ImageView player() {
        ImageView i = new ImageView(new Image("file:src/main/resources/com/example/spaceinvadersonline/s2.png"));
        i.setLayoutX(225);
        i.setLayoutY(650);
        i.setFitHeight(50);
        i.setFitWidth(50);
        return i;
    }

    public Circle shoot(double x, double y) {
        Circle c = new Circle();
        c.setFill(Color.GREENYELLOW);
        c.setLayoutX(x);
        c.setLayoutY(y);c.setRadius(3);
        return c;
    }

    public void playerShoot(Pane root, double x) {
        shoots.add(shoot((x + 25), 650));
        root.getChildren().add(shoots.get(shoots.size() - 1));
    }

    public void playersShootUpdate(Pane root) {
        if(!shoots.isEmpty()) {
            for(int i = 0; i < shoots.size(); i ++) {
                shoots.get(i).setLayoutY(shoots.get(i).getLayoutY() - 3);
                if(shoots.get(i).getLayoutY() <= 0) {
                    root.getChildren().remove(shoots.get(i));
                    shoots.remove(i);
                }
            }
        }
    }

    public void isPlayerDestroyed(Monster monster, Text lives) {
        for(int i = 0; i < monster.shoots.size(); i ++) {
            if(        ((monster.shoots.get(i).getLayoutX() > player.getLayoutX())
                    && ((monster.shoots.get(i).getLayoutX() < player.getLayoutX() + 50))
                    && ((monster.shoots.get(i).getLayoutY() > player.getLayoutY())
                    && ((monster.shoots.get(i).getLayoutY() < player.getLayoutY() + 50))))) {
                player.setLayoutX(225);
                numLives -= 1;
                lives.setText("Lives: " + numLives);
            }
        }
    }

    public void isWin(Pane root, AnimationTimer timer, Monster monster){
        if(monster.monsters.isEmpty()) {
            Text text = new Text();
            text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
            text.setX(180);
            text.setY(300);
            text.setFill(Color.YELLOW);
            text.setStrokeWidth(3);
            text.setStroke(Color.GOLD);
            text.setText("WIN");
            root.getChildren().add(text);
            timer.stop();
        }
    }

    public void isLost(Pane root, AnimationTimer timer){
        if(numLives <= 0) {
            Text text = new Text();
            text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
            text.setX(180);
            text.setY(300);
            text.setFill(Color.RED);
            text.setStrokeWidth(3);
            text.setStroke(Color.CRIMSON);
            text.setText("LOST");
            root.getChildren().add(text);
            timer.stop();
        }
    }
}
