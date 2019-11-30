package game;

import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class CherryBomb extends Plant {
    private int row;
    private int column;
    private int posX;
    private int posY;
    private int price = 150;
    private int[] position = new int[2];
    private int health = 100000;
    private int power = 0;
    private transient StackPane s = new StackPane();
    private transient Pane root = new Pane();
    private transient Image plant;
    private transient ImageView plantView;
    private boolean isDie = false;

    public CherryBomb(int row, int column, int posX, int posY, Pane root, Player pl) {

        try{
            this.plant = new Image(Files.newInputStream(Paths.get("src/res/cherry-bomb.gif")));
        }catch(Exception e){}

        this.posX = posX;
        this.posY = posY;
        this.row = row;
        this.column = column;
        this.plantView = new  ImageView(plant);
        s.getChildren().add(plantView);
        this.root = root;
        root.getChildren().add(s);
        s.setTranslateY(this.posY);
        s.setTranslateX(this.posX);
    }
    
    public int getRow(){
        return this.row;
    }

    public int getColumn(){
        return this.column;
    }

    public void removeImage(){
        this.s.getChildren().remove(this.plantView);
        this.root.getChildren().remove(this.s);
    }
    public void setPosition(int row, int column){
        this.row = row;
        this.column = column;
    }

    public void setSize(double width, double height) {
        this.plantView.setFitWidth(width);
        this.plantView.setFitHeight(height);
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

    public int getPosX(){
        return this.posX;
    }

    public int getPosY(){
        return this.posY;
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
    public void step(){
        
    }

    public void makeSound() {

    }

    public void removeStar(){

    }

    public String getName(){
        return "cherrybomb";
    }
}
