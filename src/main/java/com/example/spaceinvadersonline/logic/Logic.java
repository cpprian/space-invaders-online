package com.example.spaceinvadersonline.logic;

import com.example.spaceinvadersonline.server.DataPackage;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class Logic {
    public Player player1;
    public Player player2;
    public Monster monster;
    public House house;
    int score;
    public Logic(DataPackage player1, DataPackage player2) {
        this.player1 = new Player(player1.playerX, player1.playerID, player1.playerName, player1.playerPoints, player1.playerLives);
        this.player2 = new Player(player2.playerX, player2.playerID, player2.playerName, player2.playerPoints, player2.playerLives);
        monster = new Monster();
        house = new House();
        score = 0;
    }

    public void gameUpdate(Pane root, AnimationTimer timer, Text points1, Text lives1, Text points2, Text lives2) {
        player1.playersShootUpdate(root);
        player2.playersShootUpdate(root);
        monster.monstersShootUpdate(root);
        house.isHouseDamaged(root, monster);
        player1.isPlayerDestroyed(monster, lives1);
        player2.isPlayerDestroyed(monster, lives2);
        monster.isMonsterDestroyed(root, player1, score, points1);
        monster.isMonsterDestroyed(root, player2, score, points2);
        monster.monstersMove(player1);
        monster.monstersMove(player2);
        player1.isWin(root, timer, monster);
        player2.isWin(root, timer, monster);
        player1.isLost(root, timer);
        player2.isLost(root, timer);
    }
}
