package game;

import javafx.geometry.Pos;

import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;

/*
This class was made with referenced from an online source I forgot.
*/


public class MenuItem extends StackPane {

    int buttonNum;

    public MenuItem(String name) {
        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop[] {
                new Stop(0, Color.DARKBLUE),
                new Stop(0.1, Color.BLUE),
                new Stop(0.9, Color.BLUE),
                new Stop(1, Color.DARKBLUE)
        });

        Rectangle bg = new Rectangle(200, 30);
        bg.setOpacity(0.4);

        Text text = new Text(name);
        text.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 30));
        text.setFill(Color.WHITE);

        setAlignment(Pos.CENTER);
        getChildren().addAll(bg, text);

        setOnMouseExited(event -> {
            bg.setFill(Color.BLACK);
            text.setFill(Color.WHITE);
        });

        setOnMousePressed(event -> {
            bg.setFill(Color.GREENYELLOW);
        });

        setOnMouseEntered(event -> {
            bg.setFill(gradient);
            text.setFill(Color.WHITE);
        });

        setOnMouseReleased(event -> {
            bg.setFill(gradient);
        });
    }
}