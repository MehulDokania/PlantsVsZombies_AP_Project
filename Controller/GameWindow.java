package game;

import javax.swing.*;
//import javax.xml.bind.annotation.XmlElement.DEFAULT;

import java.io.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

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

public class GameWindow{
    
    static Stage window;
    static Scene gameplay;
    static Scene menu;
    static int difficulty = 3;
    static Player player;
    static Label sun1;
    static GameController controller;
    static Label timerLabel;
    static String playerName;
    static TextField playerNameTF;

    protected static Pane createContent(int diffi, GameController gc) throws IOException {

        Pane root = new Pane();
        root.setPrefSize(860, 600);

        difficulty = diffi;


        window = MainMenu.window;
        menu = MainMenu.menu;
        //gameplay = MainMenu.gameplay;
        playerName = "NoOne";        

        InputStream is;
        ImageView img;
        Image peashooter=null,sunflower=null,wallnut=null,cherrybomb=null;
        Image plantpane = null;
        Button back = new Button();

        timerLabel = new Label();  

        try{
            //timer
            timerLabel.setText("0");
            timerLabel.setTextFill(Color.GREEN);
            timerLabel.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
            timerLabel.setStyle("-fx-font-size: 3em;-fx-font-weight: bold");
            
            timerLabel.setLayoutX(470);
            timerLabel.setLayoutY(-1);
            timerLabel.setScaleX(0.6);
            timerLabel.setScaleY(0.6);

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
            root.getChildren().add(timerLabel);

            sunflower = new Image(Files.newInputStream(Paths.get("src/res/sunflowercard.jpg")));
            peashooter = new Image(Files.newInputStream(Paths.get("src/res/peashootercard.jpg")));
            wallnut = new Image(Files.newInputStream(Paths.get("src/res/wallnutcard.jpg")));
            cherrybomb = new Image(Files.newInputStream(Paths.get("src/res/cherrybombcard.jpg")));
            plantpane = new Image(Files.newInputStream(Paths.get("src/res/pane2.png")));

        }
        catch (IOException e) {
            System.out.println("Couldn't load image");
        }

        // add peashooter, sunflower and wallnut
        StackPane s1 = new StackPane();
        s1.getChildren().add(new ImageView(peashooter));
        s1.setMaxSize(90, 160);
        s1.setTranslateX(-65);
       
        StackPane s2 = new StackPane();
        s2.getChildren().add(new ImageView(sunflower));
        s2.setMaxSize(90, 160);
        s2.setTranslateX(-130);

        StackPane s3 = new StackPane();
        s3.getChildren().add(new ImageView(wallnut));
        s3.setMaxSize(90, 160);
        s3.setTranslateX(-10);


        StackPane s4 = new StackPane();
        s4.getChildren().add(new ImageView(cherrybomb));
        s4.setMaxSize(90, 160);
        s4.setTranslateX(55);

        // add the plant bar
        ImageView barView = new ImageView();
        barView.setImage(plantpane);
        StackPane bar = new StackPane();
        BorderPane sun = new BorderPane();
        root.getChildren().add(bar);
        bar.getChildren().add(sun);
        bar.getChildren().add(barView);
        bar.getChildren().add(s1);
        bar.getChildren().add(s2);
        bar.getChildren().add(s3);
        bar.getChildren().add(s4);

        // add the label to show the value of sun(which decides if you can buy the plant)
        sun1 = new Label(Integer.toString(200));
        sun1.setFont(new Font(20));
       // sun1.setTextFill(Color.BLACK);
        sun1.setTranslateX(20);
        sun1.setTranslateY(63);
        root.getChildren().add(sun1);

        // set ID for the drag and dragover and dragdrop
        s1.setId("peashooter");
        s2.setId("sunflower");
        s3.setId("wallnut");
        s4.setId("cherrybomb");

        s1.setOnDragDetected(new PlantDragController(s1, peashooter));
        s2.setOnDragDetected(new PlantDragController(s2, sunflower));
        s3.setOnDragDetected(new PlantDragController(s3, wallnut));
        s4.setOnDragDetected(new PlantDragController(s4, cherrybomb));


       // s1.setOnDragDone(new PlantDragDropController());

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
                GameController.timer.cancel();
                int ans = ConfirmBox.display();
                System.out.println("ans is " + ans);
                if(ans==1){
                    window.setScene(menu);
                }
                else if(ans==2 || ans == 0){
                    controller.startTimer();
                }
                else if(ans==3){
                    try{
                        System.out.println("Saving " + MainMenu.playerName);;
                        serialize(MainMenu.playerName);
                        GameController.timer.cancel();
                        window.setScene(menu);
                    }catch(Exception ee){}
                }
            }
        });
        
        if(gc==null){
            player = new Player(difficulty,root,sun1);
            controller = new GameController(difficulty,player,player.enemy,window);
            controller.startTimer();
        }else{
            controller = gc;
            player = gc.player;
            player.setPane(root);
            gc.player.enemy.root = root;
            gc.setStage(window);
            gc.setup();
            for(Zombie zm : gc.enemy.getZombies()){
                System.out.println("IMGZ x: " + zm.getImagePositionX());
            }
            gc.startTimer(); 
        }

        System.out.println("Timer started");

        return root;
    }

    public static void serialize(String name) throws IOException {
        
        ObjectOutputStream out = null;
        try {
            if(!GameController.saveNameList.contains(name)){
                //System.out.println("SIZE OF SAVELIST: " + controller.saveNameList.size());
                GameController.saveNameList.add(name);
                //System.out.println("SIZE OF SAVELIST: " + controller.saveNameList.size());
            }
            out = new ObjectOutputStream (new FileOutputStream(name + "PvZMDSG.txt"));
            player.playerName = MainMenu.playerName;
            out.writeObject(controller);
            
        } 
        catch(Exception e){
            System.out.println("Unable to Serialize. Unable to Save Game.");
            e.printStackTrace();
        }
        finally {
            out.close();
        }
        
    }

    public static GameController deserialize(String name) throws IOException,ClassNotFoundException{
        
        ObjectInputStream in = null;
        try {
            
            in = new ObjectInputStream (Files.newInputStream(Paths.get(name + "PvZMDSG.txt")));
            
            controller = (GameController) in.readObject();
        } 
        catch(Exception e){
            System.out.println("Unable to DeSerialize. Unable to Load Game.");
            e.printStackTrace();
        }
        finally {
            in.close();
        }
        return controller;
    }
	
}
