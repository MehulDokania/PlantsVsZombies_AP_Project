package game;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.stage.Modality;
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

public class ConfirmBox {

    private static int answer = 0;

    // answer = 1 -> back to menu
    // answer = 2 -> back to game
    // answer = 3 -> save and exit


    public static int display(){

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(250);

        Pane root = new Pane();
        Button tomenu = new Button();
        Button togame = new Button();

        InputStream is;
        ImageView img;

        root.setPrefSize(300, 300);

        try{

            is = Files.newInputStream(Paths.get("src/res/pausebg.jpg"));
            img = new ImageView(new Image(is));
            img.setFitWidth(300);
            img.setFitHeight(300);
            root.getChildren().add(img);

        //menu back button
            is = Files.newInputStream(Paths.get("src/res/backtomenu.jpg"));
            img = new ImageView(new Image(is));
            img.setFitWidth(110);
            img.setFitHeight(40);
            tomenu.setGraphic(img);
            //System.out.println(back.getPadding());
            tomenu.setPadding(new Insets(-0.3f,-0.3f,-0.3f,-0.3f));
        //  System.out.println(back.getPadding());
            tomenu.setLayoutX(180);
            tomenu.setLayoutY(250);
        //back.setVisible(false);

            is = Files.newInputStream(Paths.get("src/res/backtogame.jpg"));
            img = new ImageView(new Image(is));
            img.setFitWidth(140);
            img.setFitHeight(40);
            togame.setGraphic(img);
            //System.out.println(back.getPadding());
            togame.setPadding(new Insets(-0.3f,-0.3f,-0.3f,-0.3f));
        //  System.out.println(back.getPadding());
            togame.setLayoutX(10);
            togame.setLayoutY(250);

            root.getChildren().add(tomenu);
            root.getChildren().add(togame);



        }catch(Exception e){
            System.out.println("Error loading back to images");
        }

        DropShadow shadow = new DropShadow();
        //Adding the shadow when the mouse cursor is on
        tomenu.addEventHandler(MouseEvent.MOUSE_ENTERED, 
            new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent e) {
                    tomenu.setEffect(shadow);
                }
        });
        //Removing the shadow when the mouse cursor is off
        tomenu.addEventHandler(MouseEvent.MOUSE_EXITED, 
            new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent e) {
                    tomenu.setEffect(null);
                }
        });

        tomenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                answer = 1;
                window.close();
            }
        });


        //Adding the shadow when the mouse cursor is on
        togame.addEventHandler(MouseEvent.MOUSE_ENTERED, 
        new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent e) {
                    togame.setEffect(shadow);
                }
        });
        //Removing the shadow when the mouse cursor is off
        togame.addEventHandler(MouseEvent.MOUSE_EXITED, 
            new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent e) {
                    togame.setEffect(null);
                }
        });

        togame.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                answer = 2;
                window.close();
            }
        });


        Scene pause = new Scene(root);
        window.setScene(pause);
        window.showAndWait();



       

        return answer;

    }

}