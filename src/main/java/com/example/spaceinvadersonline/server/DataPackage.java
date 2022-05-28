package com.example.spaceinvadersonline.server;

import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class DataPackage {
    public String playerName;
    public int playerID;
    public int playerPoints;
    public int playerLives;
    public int playerX;
//    public ArrayList<Circle> shoots;

    public DataPackage(String playerName, int playerID, int playerPoints, int playerLives, int playerX) {
        this.playerName = playerName;
        this.playerID = playerID;
        this.playerPoints = playerPoints;
        this.playerLives = playerLives;
        this.playerX = playerX;
    }
}
