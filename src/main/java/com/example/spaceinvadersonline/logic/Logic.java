package com.example.spaceinvadersonline.logic;

import com.example.spaceinvadersonline.data.DataPackage;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class Logic {
    public Player player1;
    public Player player2;
    public Monster monster;
    public House house;
    int score1;
    int score2;
    public Logic(DataPackage currentPlayer, DataPackage secondPlayer) {
        if (currentPlayer.getId() == 0) {
            player1 = new Player(currentPlayer.getxPosition(), currentPlayer.getId(), currentPlayer.getName(), 0, 3);
            player2 = new Player(secondPlayer.getxPosition(), secondPlayer.getId(), secondPlayer.getName(), 0, 3);
        } else {
            player1 = new Player(secondPlayer.getxPosition(), secondPlayer.getId(), secondPlayer.getName(), 0, 3);
            player2 = new Player(currentPlayer.getxPosition(), currentPlayer.getId(), currentPlayer.getName(), 0, 3);
        }
        monster = new Monster();
        house = new House();
        score1 = 0;
        score2 = 0;
    }

    public void gameUpdate(Pane root, AnimationTimer timer, Text points1, Text lives1, Text points2, Text lives2) {
        player1.playersShootUpdate(root);
        player2.playersShootUpdate(root);
        monster.monstersShootUpdate(root);
        house.isHouseDamaged(root, monster);
        player1.isPlayerDestroyed(monster, lives1);
        player2.isPlayerDestroyed(monster, lives2);
        monster.isMonsterDestroyed(root, player1, score1, points1);
        monster.isMonsterDestroyed(root, player2, score2, points2);
        monster.monstersMove(player1, player2);
        player1.isWin(root, timer, monster);
        player2.isWin(root, timer, monster);
        player1.isLost(root, timer);
        player2.isLost(root, timer);
    }

    public void updatePlayer(DataPackage player) {
        // TODO: udpate shoots
        if (player.getId() == 0) {
            player1.setPlayer(player.getxPosition(), player.getLives(), player.getPoints());
        } else {
            player2.setPlayer(player.getxPosition(), player.getLives(), player.getPoints());
        }
    }
}
