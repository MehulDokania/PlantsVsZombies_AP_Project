package game;

import java.util.*;

import javafx.scene.layout.*;
import javafx.scene.shape.*;

public class MenuBox extends VBox {

    public MenuBox(ArrayList<MenuItem> items) {
        getChildren().add(createSeparator());

        for (MenuItem item : items) {
            getChildren().addAll(item, createSeparator());
        }
    }

    public MenuBox(MenuItem... items) {
        getChildren().add(createSeparator());

        for (MenuItem item : items) {
            getChildren().addAll(item, createSeparator());
        }
    }

    private Line createSeparator() {
        Line separator = new Line();
        separator.setEndX(200);
        separator.setEndY(20);
        return separator;
    }
}
