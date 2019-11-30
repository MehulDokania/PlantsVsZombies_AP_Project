package game;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Pair;

public class StrongZombie extends Zombie implements Serializable{
    private int row;
    private int column;
    private int health = 500;
    private int power = 10;
    private transient StackPane s = new StackPane();
    private transient Group root = new Group();
    private transient Image zombie;
    transient ImageView zombieView;
    private double imageY;
    private double imageX;
    private double speed = -0.008;
    private double ispeed = -0.008;
    private String name;
    private int posX,posY;

    public StrongZombie(int row, int column, int posX, int posY, Pane root){
        this.row = row;
        this.column = column;
        try{
            this.zombie = new Image(Files.newInputStream(Paths.get("src/res/strongzombie.gif")));
        }catch(Exception e){
            System.out.println("Couldnt load strong zombie img");
        }

        this.posX = posX;
        this.posY = posY;

        this.zombieView = new  ImageView(zombie);
        s.getChildren().add(zombieView);
        // convert the row and column to the position at the background

        // int X = 200;//Math.max(800,205 + (column-1)*40);
        // int Y = 80 + (row-1)*90 ;

        this.name = "strongZ";

        System.out.println("Created Strong Zombie " + posX + " " + posY  + "   row: " + row + "   col: " + column);

        s.setTranslateY(posX) ;
        s.setTranslateX(posY);
        this.imageX = posX;
        this.imageY = posY;
        root.getChildren().add(s);
    }

    public void removeImage(){
        this.s.getChildren().remove(this.zombieView);
        this.root.getChildren().remove(this.s);
    }

    public void setImagePosition(double x, double y){
        this.imageX = x;
        this.imageY = y;
    }

    public double getImagePositionX(){
        return this.imageX;
    }

    public double getImagePositionY() {
        return this.imageY;
    }

    public int getRow(){
        return this.row;
    }

    public int getColumn(){
        Pair<Integer,Integer> aa = MainMenu.getCell((int)getImagePositionX(), (int)getImagePositionX());
        return aa.getValue();
    }

    public void setColumn (int column){
        this.column = column;
    }

    public int getHealth(){
        return health;
    }

    public void setHealth(int health){
        this.health = health;

    }

    public int getPosX(){
        return this.posX;
    }

    public int getPosY(){
        return this.posY;
    }

    public int getPower(){
        return power;
    }

    public double getISpeed(){
        return ispeed;
    }

    public void setSpeed(double speed){
        this.speed = speed;

    }

    public void step(){
        double imageX = getImagePositionX()+this.speed;
        setImagePosition(imageX, imageY);
        s.setTranslateY(this.imageY);
        s.setTranslateX(imageX);
    }

    public String getName(){
        return this.name;
    } 
}
