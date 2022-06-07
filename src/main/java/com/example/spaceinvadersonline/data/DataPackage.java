package com.example.spaceinvadersonline.data;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class DataPackage {
    private String name;
    private int id;
    private int xPosition;
    private int lives;
    private int points;
    private boolean isWin;
    private boolean isShooting;
    private ArrayList<Integer> shoots;

    public DataPackage(String name, int id, int xPosition) {
        this.name = name;
        this.id = id;
        this.xPosition = xPosition;
        this.lives = 3;
        this.points = 0;
        this.isWin = false;
        this.isShooting = false;
        this.shoots = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isWin() {
        return isWin;
    }

    public void setWin(boolean win) {
        isWin = win;
    }
    public void setShooting(boolean shooting) {
        isShooting = shooting;
    }
    public boolean isShooting() {
        return isShooting;
    }

    public ArrayList<Integer> getShoots() {
        return shoots;
    }

    public void setShoots(ArrayList<Circle> bullets) {
        if (bullets.size() > 10) {
            System.out.println("Too many bullets");
            return;
        }
        for (int i = 0; i < bullets.size(); i++) {
            if (shoots.size() < bullets.size()) {
                shoots.add((int) bullets.get(i).getLayoutX());
            } else {
                shoots.set(i, (int) bullets.get(i).getLayoutX());
            }
        }
    }
}