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

public class NormalZombie extends Zombie {
    private int row;
    private int column;
    private int health = 500;
    private int power = 5;
    private transient StackPane s = new StackPane();
    private transient Group root = new Group();
    private transient Image zombie;
    transient ImageView zombieView;
    private double imageY;
    private double imageX;
    private double speed = -0.007;
    private double ispeed = -0.007;
    private String name;
    private int posX, posY;

    public NormalZombie(int row, int column, int posX, int posY, Pane root){
        this.row = row;
        this.column = column;
        try{
            this.zombie = new Image(Files.newInputStream(Paths.get("src/res/strongzombie.gif")));
        }catch(Exception e){
            System.out.println("Could not load normal zombie image");
        }

        this.posX = 200;
        this.posY = posY;
        this.name = "normalZ";

        this.zombieView = new ImageView(zombie);
        s.getChildren().add(zombieView);
        root.getChildren().add(s);
       
        // int X = Math.max(800,205 + (column-1)*40);
        // int Y = 60 + (row-1)*90 ;
        //this.posX = 200;
        System.out.println("Created Normal Zombie " + posX + " " + posY );

        // convert the row and column to the position at the background
        s.setTranslateX(posX);//(int) 50);//(135 + (row - 1) * 110 + 55) - 40 + 1);
        s.setTranslateY(posY);//(int) 50);//(60 + (column - 1) * 80 + 40) - 40);
        this.imageX = posX;//60 + (column - 1) * 80 + 40 + 40;
        this.imageY = posY;//135 + (row - 1) * 110 + 55 - 40;
    }

    public void removeImage() {
        this.s.getChildren().remove(this.zombieView);
        this.root.getChildren().remove(this.s);
    }

    public int getPosX(){
        return this.posX;
    }

    public int getPosY(){
        return this.posY;
    }

    public void setImagePosition(double x, double y) {
        this.imageX = x;
        this.imageY = y;
    }

    public double getImagePositionX() {
        return this.imageX;
    }

    public double getImagePositionY() {
        return this.imageY;
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        Pair<Integer,Integer> aa = MainMenu.getCell((int)getImagePositionX(), (int)getImagePositionX());
        System.out.println(aa.getValue());
        return aa.getValue();
    }

    public int getPower() {
        return power;
    }

    public double getISpeed() {
        return ispeed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;

    }

    public void step() {
        double imageX = getImagePositionX() + this.speed;
        setImagePosition(imageX, imageY);
        s.setTranslateY(this.imageY);
        s.setTranslateX(imageX);
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;

    }

    public String getName(){
        return this.name;
    } 
}