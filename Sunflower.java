package game;

import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;


public class Sunflower extends Plant {
    private int row;
    private int column;
    private int posX;
    private int posY;
    private int price = 50;
    private int[] position = new int[2];
    private int health = 20000;
    private int power = 0;
    private transient StackPane s = new StackPane();
    private transient Pane root = new Pane();
    private transient Image plant;
    private transient ImageView plantView;
    private Player player;
    private int shoot_count=0;
    private String name = "";
    private transient Image sun;
    private transient ImageView sunView;
    private boolean hasSun = false;
    private transient StackPane s2 = new StackPane();



    public Sunflower(int row, int column,int posX, int posY, Pane root, Player player){

        try{
            this.sun = new Image(Files.newInputStream(Paths.get("src/res/sun.png")));
            this.plant = new Image(Files.newInputStream(Paths.get("src/res/sunflower.gif")));
        }catch(Exception e){}

        this.posX = posX;
        this.posY = posY;
        //this.timeToBuy = 10;
        this.sunView = new ImageView(sun);
        this.s2.getChildren().add(sunView);
        this.player = player;
        this.row = row;
        this.column = column;
        this.plantView = new  ImageView(plant);
        s.getChildren().add(plantView);
        this.root = root;
        root.getChildren().add(s);
        s.setTranslateY(this.posY);
        s.setTranslateX(this.posX);
    }

    public void removeImage(){
        this.s.getChildren().remove(this.plantView);
        this.root.getChildren().remove(this.s);
        this.s2.getChildren().remove(this.sunView);
        this.root.getChildren().remove(this.s2);
    }

    public void removeSun(){
        this.s2.getChildren().remove(this.sunView);
        this.root.getChildren().remove(this.s2);
    }

    public int getRow(){
        return this.row;
    }

    public int getColumn(){
        return this.column;
    }

    public void setPosition(int row, int column){
        this.row = row;
        this.column = column;
    }

    public int getPosX(){
        return this.posX;
    }

    public int getPosY(){
        return this.posY;
    }

    public int[] getPosition(){
        return position;
    }

    public int getHealth(){
        return health;
    }

    public void setHealth(int health){
        this.health = health;
    }

    public int getPower(){
        return power;
    }

    public void setPower(int power){
        this.power = power;
    }

    public int getPrice(){
        return price;
    }

    public void setPrice(int price){
        this.price = price;
    }

    public void setSize(double width, double height) {
        this.plantView.setFitWidth(width);
        this.plantView.setFitHeight(height);
    }

    public void step(){
        
        if (shoot_count == 10000){
            StackPane s2 = new StackPane();
            s2.getChildren().add(this.sunView);
            s2.setTranslateY(this.posY);
            s2.setTranslateX(this.posX);
            root.getChildren().add(s2);
            this.s2 = s2;
            shoot_count = 0;
            hasSun = true;
        } else {
            shoot_count ++;
        }
    }
    public void makeSound() {

    }

    public boolean isHasSun(){
        return hasSun;
    }

    public void setHasSun(boolean bool){
        hasSun = bool;
    }

    public String getName(){
        return "sunflower";
    }
}
