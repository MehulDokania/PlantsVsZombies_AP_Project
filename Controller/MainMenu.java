package game;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.nio.file.Path;

import javax.swing.*;

import java.io.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

import game.PlantDragController;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.stage.Stage;
import javafx.util.Duration;
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

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.scene.transform.Scale;

public class MainMenu extends Application {

    static Stage window;
    static Scene gameplay,menu,loadSaved,lvlchoose;
    static Player player;
    static String playerName = "NoOne";
    
    static Pane root;
    static int difficulty = 1;

    private Parent createContent() throws IOException {

        root = new Pane();
        
        //root.setPrefSize(860, 600);
        Path currentDir = Paths.get(".");
        System.out.println(currentDir.toAbsolutePath());
        try{
            InputStream is = Files.newInputStream(Paths.get("src/res/menuBG.jpg"));
            ImageView img = new ImageView(new Image(is));
            img.setFitWidth(860);
            img.setFitHeight(600);
            root.getChildren().add(img);
        }
        catch (IOException e) {
            System.out.println("Couldn't MenuBG load image");
        }

        TextField playerNameTF = new TextField();
        Label playnm = new Label();
        playnm.setText("Enter Name: ");

        playnm.setTextFill(Color.BLACK);
        playnm.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        playnm.setStyle("-fx-font-size: 3em;-fx-font-weight: bold");
        
        playnm.setLayoutX(140);
        playnm.setLayoutY(200);
        playnm.setScaleX(0.6);
        playnm.setScaleY(0.6);

        playerNameTF.setOnAction( new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 
                playerName = playerNameTF.getText() ; 
                System.out.println("Got Player Name " + playerName);
            } 
        });

        playerNameTF.setLayoutX(390);
        playerNameTF.setLayoutY(210);

        root.getChildren().add(playerNameTF);
        root.getChildren().add(playnm);

        MenuItem itemExit = new MenuItem("EXIT");
        MenuItem newgame = new MenuItem("NEW GAME");
        MenuItem loadgame = new MenuItem("LOAD GAME");
        MenuItem levelgame = new MenuItem("LEVEL");

        itemExit.setOnMouseClicked(event -> System.exit(0));
        newgame.setOnMouseClicked(event -> {
            Pane roott = null;
            try{
                //loadSaved = new Scene(loadWindow.createContent());
                roott = GameWindow.createContent(difficulty,null);
            }catch(Exception e){System.out.println("Cannot tranisition to new game");}
            System.out.println("here here");
            gameplay = new Scene(roott);
            player = GameWindow.player;
            gameplay.setOnDragOver(new PlantDragOverController());
            gameplay.setOnDragDropped(new PlantDragDropController(roott, player));
            gameplay.setOnMouseClicked(new SunController(player,roott));
           
            window.setScene(gameplay); 
            letterbox(gameplay, roott);

        });
        loadgame.setOnMouseClicked(event -> {
            try{
                window.setScene(new Scene(loadWindow.createContent()));
                
            }catch(Exception e){System.out.println("failed to load LoadScreen");e.printStackTrace();}
            // Pane roott = null;
            // GameController gc = null;

            // try{
            //     window.setScene(new Scene(loadWindow.createContent()));
            //     gc = GameWindow.deserialize("mehul");
            // }catch(Exception e){System.out.println("failed deserialize");}

            // try{
            //     loadSaved = new Scene(loadWindow.createContent());
            //     roott = GameWindow.createContent(difficulty,gc);
            // }catch(Exception e){System.out.println("Cannot tranisition to new game");}
            // System.out.println("done done");
            // gameplay = new Scene(roott);
            // player = GameWindow.player;
            
            // gameplay.setOnDragOver(new PlantDragOverController());
            // gameplay.setOnDragDropped(new PlantDragDropController(roott, player));
            // gameplay.setOnMouseClicked(new SunController(player,roott));
           
            // window.setScene(gameplay); 
            // letterbox(gameplay, roott);

        });

        levelgame.setOnMouseClicked(event -> window.setScene(lvlchoose));

        MenuBox menu = new MenuBox(
                newgame,
                loadgame,
                levelgame,
                itemExit);
        menu.setTranslateX(350);
        menu.setTranslateY(250);

        root.getChildren().add(menu);
        return root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        window = primaryStage;
        window.setMinHeight(600);
        window.setMinWidth(860);
        window.setMaxHeight(1200);
        window.setMaxWidth(860);
        
        menu = new Scene(createContent());
        //loadSaved = new Scene(loadWindow.createContent());
        lvlchoose = new Scene(levelWindow.createContent());
        window.setTitle("PvZ Menu");
        window.setScene(menu);
        window.show();
        letterbox(menu, root);        

        ExecutorService es = Executors.newFixedThreadPool(3);
        Game gm = new Game();
        es.execute(gm);

    }
    
    /*
    The below section of the code (letterbox part) was taken from stackOverflow when I was trying to scale the window.
    This does not belong to me.
    */
    private void letterbox(final Scene scene, final Pane contentPane) {
        final double initWidth  = scene.getWidth();
        final double initHeight = scene.getHeight();
        final double ratio      = initWidth / initHeight;
    
        SceneSizeChangeListener sizeListener = new SceneSizeChangeListener(scene, ratio, initHeight, initWidth, contentPane);
        scene.widthProperty().addListener(sizeListener);
        scene.heightProperty().addListener(sizeListener);
      }
    
      private static class SceneSizeChangeListener implements ChangeListener<Number> {
        private final Scene scene;
        private final double ratio;
        private final double initHeight;
        private final double initWidth;
        private final Pane contentPane;
    
        public SceneSizeChangeListener(Scene scene, double ratio, double initHeight, double initWidth, Pane contentPane) {
          this.scene = scene;
          this.ratio = ratio;
          this.initHeight = initHeight;
          this.initWidth = initWidth;
          this.contentPane = contentPane;
        }
    
        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
          final double newWidth  = scene.getWidth();
          final double newHeight = scene.getHeight();
    
          double scaleFactor =
              newWidth / newHeight > ratio
                  ? newHeight / initHeight
                  : newWidth / initWidth;
    
          if (scaleFactor >= 1) {
            Scale scale = new Scale(scaleFactor, scaleFactor);
            scale.setPivotX(0);
            scale.setPivotY(0);
            scene.getRoot().getTransforms().setAll(scale);
    
            contentPane.setPrefWidth (newWidth  / scaleFactor);
            contentPane.setPrefHeight(newHeight / scaleFactor);
          } else {
            contentPane.setPrefWidth (Math.max(initWidth,  newWidth));
            contentPane.setPrefHeight(Math.max(initHeight, newHeight));
          }
        }
      }

    
    public static Pair getCell( int posX, int posY ){
        int r = -1,c = -1;
        int sX = 205;
        int eX = 770;
        int difX = (eX-sX)/9; 

        int sY = 110;
        int eY = 580;
        int difY = (eY-sY)/5;

        if (posX >= sX && posX <= eX) {
            if (posY >= sY && posY <= eY) {
                for(int i=1;i<=5;i++){
                    if(posY <= sY + i*difY ){
                        r = i;
                        break;
                    }
                }

                for(int i=1;i<=9;i++){
                    if(posX <= sX + i*difX ){
                        c = i;
                        break;
                    }
                }
            }
        }

        Pair<Integer,Integer> ans = new Pair(r,c);

        return ans;

    }  


    public static void main(String[] args) {
        launch(args);
    }

}

