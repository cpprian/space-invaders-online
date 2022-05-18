package com.example.spaceinvadersonline.logic;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class House {
    public List<ImageView> houses = new ArrayList<>();
    private final List<Integer> house_damage = new ArrayList<>();

    static final private String house = "file:src/main/resources/com/example/spaceinvadersonline/house/house.png";
    static final private String house_d1 = "file:src/main/resources/com/example/spaceinvadersonline/house/h1.png";
    static final private String house_d2 = "file:src/main/resources/com/example/spaceinvadersonline/house/h2.png";
    static final private String house_d3 = "file:src/main/resources/com/example/spaceinvadersonline/house/h3.png";
    static final private String house_d4 = "file:src/main/resources/com/example/spaceinvadersonline/house/h4.png";
    public void addHouses(Pane root) {
        for (int i = 0, w = 70; i < 4; i++, w += 300) {
            houses.add(house(w, 780, house));
            root.getChildren().add(houses.get(i));
            house_damage.add(0);
        }
    }

    public ImageView house(double x, double y, String h) {
        ImageView i = new ImageView(new Image(h));
        i.setLayoutX(x);
        i.setLayoutY(y);
        i.setFitHeight(200);
        i.setFitWidth(200);
        return i;
    }

    public String getNewHouseShape(Integer damage) {
        String newHouse = null;
        switch (damage) {
            case 1 -> newHouse = house_d1;
            case 2 -> newHouse = house_d2;
            case 3 -> newHouse = house_d3;
            case 4 -> newHouse = house_d4;
        }
        return newHouse;
    }

    public void isHouseDamaged(Pane root, Monster monster) {
        // shoots
        for (int i = 0; i < monster.shoots.size(); i++) {
            for (int j = 0; j < houses.size(); j++) {
                if (       monster.shoots.get(i).getLayoutX() > houses.get(j).getLayoutX()
                        && monster.shoots.get(i).getLayoutX() < houses.get(j).getLayoutX() + 200
                        && monster.shoots.get(i).getLayoutY() > houses.get(j).getLayoutY()
                        && monster.shoots.get(i).getLayoutY() < houses.get(j).getLayoutY() + 200
                ) {
                    root.getChildren().remove(monster.shoots.get(i));
                    monster.shoots.remove(i);
                    if (house_damage.get(j) != 4) {
                        house_damage.set(j, house_damage.get(j) + 1);
                        houses.get(j).setImage(new Image(getNewHouseShape(house_damage.get(j))));
                        root.getChildren().remove(houses.get(j));
                        root.getChildren().add(houses.get(j)); // FIX
                    } else {
                        root.getChildren().remove(houses.get(j));
                        houses.remove(j);
                    }
                    break;
                }
            }
        }

        // monsters
        for (int i = 0; i < monster.monsters.size(); i++) {
            for (int j = 0; j < houses.size(); j++) {
                if (    monster.monsters.get(i).getLayoutX() > houses.get(j).getLayoutX()
                        && monster.monsters.get(i).getLayoutX() < houses.get(j).getLayoutX() + 200
                        && monster.monsters.get(i).getLayoutY() > houses.get(j).getLayoutY()
                        && monster.monsters.get(i).getLayoutY() < houses.get(j).getLayoutY() + 200
                ) {
                    root.getChildren().remove(houses.get(j));
                    houses.remove(j);
                    break;
                }
            }
        }
    }
}
