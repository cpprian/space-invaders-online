package com.example.spaceinvadersonline;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class TempGame implements Initializable {
    @FXML
    private Pane rootPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initLayout();
    }

    private void initLayout() {
        rootPane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        rootPane.setStyle("-fx-background-color: black;");


        // right pane
        Rectangle rect = new Rectangle(1300, 0, 400, 1010);
        rect.setFill(Color.BLACK);
        rect.setStroke(Color.WHITE);
        rect.setStrokeWidth(2);
        rootPane.getChildren().add(rect);

        makeLogoPane();
        makePlayerNamePane("Player 1", "Player 2");

        // player1.getlives(), player2.getlives(), player1.getScore(), player2.getScore()
        // timer?
        setPlayerInfoPane(3, 3, 0, 0);
    }

    private void makeLogoPane() {
        Text l1 = new Text("SPACE");
        l1.setLayoutX(1370);
        l1.setLayoutY(100);
        l1.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 63));
        l1.setFill(Color.WHITE);
        Text logo2 = new Text("INVADERS");
        logo2.setLayoutX(1310);
        logo2.setLayoutY(160);
        logo2.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 63));
        logo2.setFill(Color.WHITE);
        rootPane.getChildren().addAll(l1,logo2);
    }

    private void makePlayerNamePane(String p1, String p2) {
        Text p1Name = new Text(p1);
        p1Name.setLayoutX(1350);
        p1Name.setLayoutY(300);
        p1Name.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        p1Name.setFill(Color.WHITE);

        Text p2Name = new Text(p2);
        p2Name.setLayoutX(1350);
        p2Name.setLayoutY(600);
        p2Name.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        p2Name.setFill(Color.WHITE);
        rootPane.getChildren().addAll(p1Name,p2Name);
    }

    private void setPlayerInfoPane(int lives1, int lives2, int points1, int points2) {
        Text lives1Text = new Text("Lives: " + lives1);
        lives1Text.setLayoutX(1350);
        lives1Text.setLayoutY(400);
        lives1Text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        lives1Text.setFill(Color.WHITE);

        Text lives2Text = new Text("Lives: " + lives2);
        lives2Text.setLayoutX(1350);
        lives2Text.setLayoutY(700);
        lives2Text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        lives2Text.setFill(Color.WHITE);

        Text points1Text = new Text("Points: " + points1);
        points1Text.setLayoutX(1350);
        points1Text.setLayoutY(500);
        points1Text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        points1Text.setFill(Color.WHITE);

        Text points2Text = new Text("Points: " + points2);
        points2Text.setLayoutX(1350);
        points2Text.setLayoutY(800);
        points2Text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        points2Text.setFill(Color.WHITE);
        rootPane.getChildren().addAll(lives1Text,lives2Text,points1Text,points2Text);
    }
}
