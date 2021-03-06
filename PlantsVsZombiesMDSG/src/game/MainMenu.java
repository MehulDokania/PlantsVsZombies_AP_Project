package game;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;

public class MainMenu extends Application {

    static Stage window;
    static Scene gameplay,menu;

    private Parent createContent() throws IOException {
        Pane root = new Pane();
        root.setPrefSize(860, 600);
        try{
            InputStream is = Files.newInputStream(Paths.get("src/res/menuBG.jpg"));
            ImageView img = new ImageView(new Image(is));
            img.setFitWidth(860);
            img.setFitHeight(600);
            root.getChildren().add(img);
        }
        catch (IOException e) {
            System.out.println("Couldn't load image");
        }

        MenuItem itemExit = new MenuItem("EXIT");
        MenuItem newgame = new MenuItem("NEW GAME");
        MenuItem loadgame = new MenuItem("LOAD GAME");
        itemExit.setOnMouseClicked(event -> System.exit(0));
        newgame.setOnMouseClicked(event -> window.setScene(gameplay));

        MenuBox menu = new MenuBox(
                newgame,
                loadgame,
                itemExit);
        menu.setTranslateX(350);
        menu.setTranslateY(250);

        root.getChildren().add(menu);
        return root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        menu = new Scene(createContent());
        gameplay = new Scene(GameWindow.createContent());
        window.setTitle("PvZ Menu");
        window.setScene(menu);
        window.show();
    }

    private static class MenuBox extends VBox {
        public MenuBox(MenuItem... items) {
            getChildren().add(createSeparator());

            for (MenuItem item : items) {
                getChildren().addAll(item, createSeparator());
            }
        }

        private Line createSeparator() {
            Line sep = new Line();
            sep.setEndX(200);
            sep.setEndY(20);
          //  sep.setStroke(Color.DARKGREY);
            return sep;
        }
    }

    private static class MenuItem extends StackPane {
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
          //  text.setFill(Color.DARKGREY);
            text.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 30));
            text.setFill(Color.WHITE);

            setAlignment(Pos.CENTER);
            getChildren().addAll(bg, text);

            setOnMouseEntered(event -> {
                bg.setFill(gradient);
                text.setFill(Color.WHITE);
            });

            setOnMouseExited(event -> {
                bg.setFill(Color.BLACK);
                text.setFill(Color.WHITE);
            });

            setOnMousePressed(event -> {
                bg.setFill(Color.GREENYELLOW);
            });

            setOnMouseReleased(event -> {
                bg.setFill(gradient);
            });
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}