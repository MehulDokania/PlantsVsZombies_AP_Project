package game;

import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

class Peashooter extends Plant{
    private int price = 100;
    private int row;
    private int column;
    private int posX;
    private int posY;
    private int health = 20000;
    private int power = 100;
    private transient StackPane s = new StackPane();
    private transient Pane root = new Pane();
    private transient Image plant;
    private transient ImageView plantView;
    private Player player;
    private int shoot_count = 900;


    public Peashooter(int row, int column,int posX, int posY, Pane root, Player player){

        try{
            this.plant = new Image(Files.newInputStream(Paths.get("src/res/peashooter.gif")));
        }catch(Exception e){}
        this.row = row;
        this.column = column;
        this.player = player;
        this.plantView = new  ImageView(plant);
        s.getChildren().add(plantView);
        this.root = root;
        root.getChildren().add(s);
        this.posX = posX;
        this.posY = posY;
        s.setTranslateY(this.posY);
        s.setTranslateX(this.posX);
    }

    public int getRow(){
        return this.row;
    }

    public int getColumn(){
        return this.column;
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

    public String getName(){
        return "peashooter";
    }

    public void setPower(int power){
        this.power = power;
    }
    public int getPrice(){
        return price;
    }
    public void step(){

        if (shoot_count == 1000 && GameWindow.controller.enemy.rowHasZombie(this.row)){
            Pea pea = new Pea (this.row,this.column,posX, this.posY, this.root,this.player);
            this.player.addPea(pea);
            pea.playSound();
            shoot_count = 0;
        } else {
            shoot_count ++;
        }
    }

    public int getPosX(){
        return this.posX;
    }

    public int getPosY(){
        return this.posY;
    }

    public void removeImage(){
        this.s.getChildren().remove(this.plantView);
        this.root.getChildren().remove(this.s);
    }

    public void setPosition(int x,int y){}
}

