package game;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

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

class loadWindow{

    static Stage window;
    static Scene loadSaved;

    static Scene gameplay,menu,lvlchoose;
    static Player player;
    static String playerName = "NoOne";
    
    static Pane root;
    static int difficulty = 1;

    protected static Pane createContent() throws IOException {

        Pane root = new Pane();
        root.setPrefSize(860, 600);

        Button back = new Button();

        window = MainMenu.window;
        menu = MainMenu.menu;
        
        gameplay = MainMenu.gameplay;
        
        InputStream is;
        ImageView img;

        ArrayList<String> lis;

        if(GameWindow.controller==null){
            System.out.println("NALA");
            lis = new ArrayList<String>();
        }
        else{
            lis = GameWindow.controller.saveNameList; 
        }

        ArrayList<MenuItem> mlis = new ArrayList<MenuItem>();

        try{
            //background
            is = Files.newInputStream(Paths.get("src/res/menuBG.jpg"));
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

            for(int i=0;i<lis.size();i++){
                mlis.add(new MenuItem(lis.get(i) + "'s Saved Game"));
            }

            MenuBox mb = new MenuBox(mlis);

            mb.setTranslateX(350);
            mb.setTranslateY(250);

            root.getChildren().add(mb);


        }
        catch(Exception e){
            System.out.println("Couldnt load LoadBG");
            e.printStackTrace();
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
                window.setScene(menu);
            }
        });
        
        for(int i=0;i<mlis.size();i++){
            MenuItem mi = mlis.get(i);
            mi.buttonNum = i;
            mi.setOnMouseClicked(event -> {

                Pane roott = null;
                GameController gc = null;

                try{
                    gc = GameWindow.deserialize(lis.get(mi.buttonNum));
                }catch(Exception e){System.out.println("failed deserialize");}

                try{
                    //loadSaved = new Scene(loadWindow.createContent());
                    roott = GameWindow.createContent(difficulty,gc);
                }catch(Exception e){System.out.println("Cannot tranisition to new game");}
                System.out.println("done done");
                gameplay = new Scene(roott);
                player = GameWindow.player;
                
                gameplay.setOnDragOver(new PlantDragOverController());
                gameplay.setOnDragDropped(new PlantDragDropController(roott, player));
                gameplay.setOnMouseClicked(new SunController(player,roott));
            
                window.setScene(gameplay);
                
            });
            
        }

        System.out.println("HEREE");

        return root;

    }
}
