package com.example.spaceinvadersonline.logic;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class Monster {
    public List<ImageView> monsters = new ArrayList<>();
    List<Circle> shoots = new ArrayList<>();
    public Circle dotR = new Circle();
    boolean toRight = true;

    static final private String monster1 = "file:src/main/resources/com/example/spaceinvadersonline/monster/k1.png";
    static final private String monster2 = "file:src/main/resources/com/example/spaceinvadersonline/monster/k2.png";
    static final private String monster3 = "file:src/main/resources/com/example/spaceinvadersonline/monster/k3.png";

    public void addMonsters(Pane root) {
        for(int i = 0, w = 40; i < 10; i++, w += 100) {
            monsters.add(monster(w, 100, monster3));
            root.getChildren().add(monsters.get(i));
        }
        for(int i = 0, w = 40; i < 10; i++, w += 100) {
            monsters.add(monster(w, 200, monster2));
            root.getChildren().add(monsters.get(i + 10));
        }
        for(int i = 0, w = 40; i < 10; i++, w += 100) {
            monsters.add(monster(w, 300, monster2));
            root.getChildren().add(monsters.get(i + 20));
        }
        for(int i = 0, w = 40; i < 10; i++, w += 100) {
            monsters.add(monster(w, 400, monster1));
            root.getChildren().add(monsters.get(i + 30));
        }
        for(int i = 0, w = 40; i < 10; i++, w += 100) {
            monsters.add(monster(w, 500, monster1));
            root.getChildren().add(monsters.get(i + 40));
        }
    }

    public ImageView monster(double x, double y, String img) {
        ImageView i = new ImageView(new Image(img));
        i.setLayoutX(x);
        i.setLayoutY(y);
        i.setFitHeight(75);
        i.setFitWidth(75);
        return i;
    }

    public void monstersShootUpdate(Pane root) {
        shootUpdate(root, shoots);
    }

    private static void shootUpdate(Pane root, List<Circle> shoots) {
        if(!shoots.isEmpty()) {
            for(int i = 0; i < shoots.size(); i ++) {
                shoots.get(i).setLayoutY(shoots.get(i).getLayoutY() + 3);
                if(shoots.get(i).getLayoutY() <= 0) {
                    root.getChildren().remove(shoots.get(i));
                    shoots.remove(i);
                }
            }
        }
    }

    public void monstersShoot(Pane root) {
        int getShootingMonsterIndex = rand(0, monsters.size() - 1);
        shoots.add(shoot(monsters.get(getShootingMonsterIndex).getLayoutX() + 25, monsters.get(getShootingMonsterIndex).getLayoutY() + 25));
        root.getChildren().add(shoots.get(shoots.size() - 1));
    }

    public void isMonsterDestroyed(Pane root, Player player, int numPoints, Text points) {
        for(int i = 0; i < player.shoots.size(); i++) {
            for(int j = 0; j < monsters.size(); j++) {
                if(        player.shoots.get(i).getLayoutX() > monsters.get(j).getLayoutX()
                        && player.shoots.get(i).getLayoutX() < monsters.get(j).getLayoutX() + 75
                        && player.shoots.get(i).getLayoutY() > monsters.get(j).getLayoutY()
                        && player.shoots.get(i).getLayoutY() < monsters.get(j).getLayoutY() + 75) {
                    root.getChildren().remove(monsters.get(j));
                    monsters.remove(j);
                    root.getChildren().remove(player.shoots.get(i));
                    player.shoots.remove(i);
                    numPoints += 100;
                    points.setText("Points: " + numPoints);
                    break;
                }
            }
        }
    }

    public void monstersMove(Player player) {
        double speed;
        if(toRight) {
            speed = 0.6;
        }
        else {
            speed = -0.6;
        }

        if(dotR.getLayoutX() >= 250) {
            toRight = false;
            for (ImageView monster : monsters) {
                monster.setLayoutY(monster.getLayoutY() + 8);
            }

        } else if(dotR.getLayoutX() <= -20) {
            toRight = true;
            for (ImageView monster : monsters) {
                monster.setLayoutY(monster.getLayoutY() + 8);
            }
        }

        for (ImageView monster : monsters) {
            monster.setLayoutX(monster.getLayoutX() + speed);
            if (monster.getLayoutY() == 800) {
                player.numLives = 0;
            }
        }
        dotR.setLayoutX(dotR.getLayoutX() + speed);
    }

    public Circle shoot(double x, double y) {
        Circle c = new Circle();
        c.setFill(Color.GREENYELLOW);
        c.setLayoutX(x);
        c.setLayoutY(y);c.setRadius(3);
        return c;
    }

    public int rand(int min, int max) {
        return (int)(Math.random() * max + min);
    }
}
