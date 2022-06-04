package com.example.spaceinvadersonline.data;

import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class DataPackage {
    private String name;
    private int id;
    private int xPosition;
    private int lives;
    private int points;
    private boolean isWin;
    private ArrayList<Integer> shoots;
    private ArrayList<Integer> monsters;

    public DataPackage() {}

    public DataPackage(String name, int id, int xPosition) {
        this.name = name;
        this.id = id;
        this.xPosition = xPosition;
        this.lives = 3;
        this.points = 0;
        this.isWin = false;
        this.shoots = new ArrayList<>();
        this.monsters = new ArrayList<>();
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

    public ArrayList<Integer> getShoots() {
        return shoots;
    }

    public void setShoots(ArrayList<Integer> shoots) {
        this.shoots = shoots;
    }

    public ArrayList<Integer> getMonsters() {
        return monsters;
    }

    public void setMonsters(ArrayList<Integer> monsters) {
        this.monsters = monsters;
    }
}
