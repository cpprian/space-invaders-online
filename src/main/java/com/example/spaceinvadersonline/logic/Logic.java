package com.example.spaceinvadersonline.logic;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class Logic {
    public Player player;
    public Monster monster;
    int score;


    public Logic() {
        player = new Player();
        monster = new Monster();
        score = 0;
    }

    public void gameUpdate(Pane root, AnimationTimer timer, Text points, Text lives) {
        monster.monstersShootUpdate(root);
        player.playersShootUpdate(root);
        player.isPlayerDestroyed(monster, lives);
        monster.isMonsterDestroyed(root, player, score, points);
        monster.monstersMove();
        player.isWin(root, timer, monster);
        player.isLost(root, timer);
    }
}
