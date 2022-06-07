package com.example.spaceinvadersonline;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Howtoplay {

    @FXML
    void onBackButton(ActionEvent event) throws IOException {
        Parent page = FXMLLoader.load(Objects.requireNonNull(Menu.class.getResource("menu.fxml")),
                null, new JavaFXBuilderFactory());
        Scene scene = new Scene(page,1700, 1010);
        scene.setFill(Color.BLACK);
        Stage gameStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        gameStage.setScene(scene);
        gameStage.show();
        gameStage.centerOnScreen();
    }

}
