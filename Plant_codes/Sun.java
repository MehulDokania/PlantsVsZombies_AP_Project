package game;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Translate;


public class Sun implements Serializable{
    private int posX;
    private int posY;
    private transient StackPane s = new StackPane();
    private transient Pane root = new Pane();
    private Player player;
    private transient Image sun;
    private transient ImageView sunView;
    Random rand = new Random();

    public Sun(Pane root, Player player){

        try{
            this.sun = new Image(Files.newInputStream(Paths.get("src/res/sun.png")));
        }catch(Exception e){}

        this.posX = (rand.nextInt(5)+1)*100;
        this.posY = (rand.nextInt(3)+1)*100;

        Translate translate = new Translate();       
        translate.setX(posX); 
        translate.setY(posY);

        this.sunView = new ImageView(sun);
        this.s.getChildren().add(sunView);
        this.s.getTransforms().addAll(translate);
        this.player = player;
        this.root = root;
        root.getChildren().add(s);
 
        //translate.setZ(100);
    }

    public void removeSun(){
        this.s.getChildren().remove(this.sunView);
        this.root.getChildren().remove(this.s);
    }
    
    public int getPosX(){
        return this.posX;
    }

    public int getPosY(){
        return this.posY;
    }

    public void step(){    

        Translate translate = new Translate();       

        translate.setX(300); 
        translate.setY(50); 
        translate.setZ(100); 

        StackPane s2 = new StackPane();
        s2.getChildren().add(this.sunView);
        s2.getTransforms().addAll(translate);
        root.getChildren().add(s2);
    }

}
