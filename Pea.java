package game;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;

public class Pea implements Serializable{
    private double velocityX = 0.2;
    private int row;
    private int column;
    private int posX;
    private int posY;
    private transient Image plant;
    private transient ImageView plantView;
    private Player player;
    private transient Pane root = new Pane();
    private transient StackPane s = new StackPane();
    private double imageY;
    private double imageX;
    private transient AudioClip audioClip;

    public Pea(int row, int col,int posX, int posY, Pane root, Player player) {
        this.row = row;
        this.column = col;
        this.player = player;
        System.out.println("PEA2 " + posX + "  " + posY);
        this.posX = posX + 90 ;
        this.posY = posY - 10;
        try{
            this.plant = new Image(Files.newInputStream(Paths.get("src/res/pea.png")));
            this.audioClip = new AudioClip(Paths.get("src/res/shoot.wav").toUri().toString());
            
        }catch(Exception e){
            System.out.println("Could not load pea pic/sound.");
        }
        this.plantView = new  ImageView(plant);
        s.getChildren().add(plantView);
        this.root = root;
        root.getChildren().add(s);
        System.out.println("PEA " + this.posX + "  " + this.posY);
        s.setTranslateY(this.posX);
        s.setTranslateX(this.posY);
        this.imageX = this.posX;
        this.imageY = this.posY;
    }

    public void playSound() {
        this.audioClip.play();
   }

    public void stopSound(){
        this.audioClip.stop();
    }

    public void removeImage(){
        this.s.getChildren().remove(this.plantView);
        this.root.getChildren().remove(this.s);
    }

    public int getRow(){
        return this.row;
    }

    public int getColumn(){
        return this.column;
    }

    public int getPosX(){
        return this.posX;
    }

    public int getPosY(){
        return this.posY;
    }
    
    public void step() {
        double imageX = getImagePositionX() + velocityX;
        //System.out.println(imageX + "   " + imageY);
        setImagePosition(imageX, this.imageY);
        s.setTranslateY(this.imageY);
        s.setTranslateX(imageX);
    }

    public void setImagePosition(double x, double y){
        this.imageX = x;
        this.imageY = y;
    }

    public double getImagePositionX(){
        return this.imageX;
    }

}