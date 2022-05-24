package com.example.spaceinvadersonline.logic;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class Logic {
    public Player player;
    public Monster monster;
    public House house;
    int score;
    public Logic() {
        player = new Player(100);
        monster = new Monster();
        house = new House();
        score = 0;
    }

    public void gameUpdate(Pane root, AnimationTimer timer, Text points1, Text lives1, Text points2, Text lives2) {
        player.playersShootUpdate(root);
        monster.monstersShootUpdate(root);
        house.isHouseDamaged(root, monster);
        player.isPlayerDestroyed(monster, lives1);
        monster.isMonsterDestroyed(root, player, score, points1);
        monster.monstersMove(player);
        player.isWin(root, timer, monster);
        player.isLost(root, timer);
    }
}
