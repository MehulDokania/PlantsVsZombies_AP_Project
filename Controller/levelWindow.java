package game;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import game.PlantDragController;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;

class levelWindow{

    static Stage window;
    static Scene choseLvl;


    protected static Pane createContent() throws IOException {

        Pane root = new Pane();
        root.setPrefSize(860, 600);

        
        

        Button lvl1 = new Button();
        Button lvl2 = new Button();
        Button lvl3 = new Button();
        Button lvl4 = new Button();
        Button lvl5 = new Button();
        Button back = new Button();

        window = MainMenu.window;
        
        //gameplay = MainMenu.gameplay;
        
        InputStream is;
        ImageView img;

        try{
            //background
            is = Files.newInputStream(Paths.get("src/res/menuBG.jpg"));
            img = new ImageView(new Image(is));
            img.setFitWidth(860);
            img.setFitHeight(600);
            root.getChildren().add(img);

            //levels
            is = Files.newInputStream(Paths.get("src/res/l1.png"));
            img = new ImageView(new Image(is));
            img.setFitWidth(110);
            img.setFitHeight(40);
            lvl1.setGraphic(img);
            lvl1.setPadding(new Insets(-0.3f,-0.3f,-0.3f,-0.3f));


            is = Files.newInputStream(Paths.get("src/res/l2.png"));
            img = new ImageView(new Image(is));
            img.setFitWidth(110);
            img.setFitHeight(40);
            lvl2.setGraphic(img);
            lvl2.setPadding(new Insets(-0.3f,-0.3f,-0.3f,-0.3f));


            is = Files.newInputStream(Paths.get("src/res/l3.png"));
            img = new ImageView(new Image(is));
            img.setFitWidth(110);
            img.setFitHeight(40);
            lvl3.setGraphic(img);
            lvl3.setPadding(new Insets(-0.3f,-0.3f,-0.3f,-0.3f));


            is = Files.newInputStream(Paths.get("src/res/l4.png"));
            img = new ImageView(new Image(is));
            img.setFitWidth(110);
            img.setFitHeight(40);
            lvl4.setGraphic(img);
            lvl4.setPadding(new Insets(-0.3f,-0.3f,-0.3f,-0.3f));


            is = Files.newInputStream(Paths.get("src/res/l5.png"));
            img = new ImageView(new Image(is));
            img.setFitWidth(110);
            img.setFitHeight(40);
            lvl5.setGraphic(img);
            lvl5.setPadding(new Insets(-0.3f,-0.3f,-0.3f,-0.3f));


            is = Files.newInputStream(Paths.get("src/res/backtomenu.jpg"));
            img = new ImageView(new Image(is));
            img.setFitWidth(110);
            img.setFitHeight(40);
            back.setGraphic(img);
            back.setPadding(new Insets(-0.3f,-0.3f,-0.3f,-0.3f));


            lvl1.setLayoutX(40);
            lvl1.setLayoutY(300);

            lvl2.setLayoutX(200);
            lvl2.setLayoutY(300);

            lvl3.setLayoutX(360);
            lvl3.setLayoutY(300);

            lvl4.setLayoutX(520);
            lvl4.setLayoutY(300);

            lvl5.setLayoutX(680);
            lvl5.setLayoutY(300);

            back.setLayoutX(730);
            back.setLayoutY(500);

            root.getChildren().add(lvl1);
            root.getChildren().add(lvl2);
            root.getChildren().add(lvl3);
            root.getChildren().add(lvl4);
            root.getChildren().add(lvl5);
            root.getChildren().add(back);


        }
        catch(Exception e){
            System.out.println("Couldnt load LoadBG");
        }

        lvl1.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    MainMenu.difficulty = 1;
                    MainMenu.window.setScene(MainMenu.menu);
                }
        });

        lvl2.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    MainMenu.difficulty = 2;
                    MainMenu.window.setScene(MainMenu.menu);
                }
        });

        lvl3.setOnAction(new EventHandler<ActionEvent>(){
                @Override public void handle(ActionEvent e) {
                    MainMenu.difficulty = 3;
                    MainMenu.window.setScene(MainMenu.menu);
                }
        });

        lvl4.setOnAction(new EventHandler<ActionEvent>(){
                @Override public void handle(ActionEvent e) {
                    MainMenu.difficulty = 4;
                    MainMenu.window.setScene(MainMenu.menu);
                }
        });

        lvl5.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    MainMenu.difficulty = 5;
                    MainMenu.window.setScene(MainMenu.menu);
                }
        });

        back.setOnAction(new EventHandler<ActionEvent>(){
                @Override public void handle(ActionEvent e) {
                    MainMenu.window.setScene(MainMenu.menu);
                }
        });

        return root;

    }
}
