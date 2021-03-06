package game;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

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

public class GameWindow{
    
    static Stage window;
    Scene gameplay;
    static Scene menu;

    protected static Parent createContent() throws IOException {
        Pane root = new Pane();
        root.setPrefSize(860, 600);

        window = MainMenu.window;
        menu = MainMenu.menu;
        
        InputStream is;
        ImageView img;

        Button back = new Button();

        try{
            //backyard
            is = Files.newInputStream(Paths.get("src/res/backyard.jpg"));
            img = new ImageView(new Image(is));
            img.setFitWidth(860);
            img.setFitHeight(600);
            root.getChildren().add(img);

            //menu back button
            is = Files.newInputStream(Paths.get("src/res/back.jpg"));
            img = new ImageView(new Image(is));
            img.setFitWidth(100);
            img.setFitHeight(40);
            back.setGraphic(img);
            //System.out.println(back.getPadding());
            back.setPadding(new Insets(-0.3f,-0.3f,-0.3f,-0.3f));
          //  System.out.println(back.getPadding());
            back.setLayoutX(750);
            //back.setVisible(false);
            root.getChildren().add(back);
            //root.getChildren().add(img);

        }
        catch (IOException e) {
            System.out.println("Couldn't load image");
        }

        DropShadow shadow = new DropShadow();
        //Adding the shadow when the mouse cursor is on
        back.addEventHandler(MouseEvent.MOUSE_ENTERED, 
            new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent e) {
                    back.setEffect(shadow);
                }
        });
        //Removing the shadow when the mouse cursor is off
        back.addEventHandler(MouseEvent.MOUSE_EXITED, 
            new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent e) {
                    back.setEffect(null);
                }
        });

        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                int ans = ConfirmBox.display();
                if(ans==1){
                    window.setScene(menu);
                }
                else if(ans==3){

                }
            }
        });

        return root;
    }
	
}
