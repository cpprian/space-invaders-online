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
    }

    public void gameUpdate(Pane root, AnimationTimer timer, Text points1, Text lives1, Text points2, Text lives2, DataPackage p1, DataPackage p2) {
        if (player1.name.equals(p1.getName())) {
            player1.playersShootUpdate(root, p1);
            player2.playersShootUpdate(root, p2);
            p1.setPoints(monster.isMonsterDestroyed(root, player1, p1.getPoints(), points1, p1));
            p2.setPoints(monster.isMonsterDestroyed(root, player2, p2.getPoints(), points2, p2));
        } else {
            player2.playersShootUpdate(root, p1);
            player1.playersShootUpdate(root, p2);
            p1.setPoints(monster.isMonsterDestroyed(root, player2, p2.getPoints(), points1, p1));
            p2.setPoints(monster.isMonsterDestroyed(root, player1, p1.getPoints(), points2, p2));
        }

        monster.monstersShootUpdate(root);
        house.isHouseDamaged(root, monster);
        player1.isPlayerDestroyed(monster, lives1);
        player2.isPlayerDestroyed(monster, lives2);
        monster.monstersMove(player1, player2);
        player1.isWin(root, timer, monster);
        player2.isWin(root, timer, monster);
        player1.isLost(root, timer);
        player2.isLost(root, timer);
    }

    public void updatePlayer(Pane rootPane, DataPackage player) {
        if (player.getId() == 0) {
            player1.setPlayer(rootPane, player.getxPosition(), player.getLives(), player.getPoints(), player.getShoots());
        } else {
            player2.setPlayer(rootPane, player.getxPosition(), player.getLives(), player.getPoints(), player.getShoots());
        }
    }
}
