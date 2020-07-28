package game;

import java.io.Serializable;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public abstract class Zombie implements Serializable{

    private int row;
    private int column;
    private int health;
    private int power;
    transient private StackPane s;
    transient private Group root;
    transient private Image zombie;
    transient ImageView zombieView;
    private double imageY;
    private double imageX;
    private double speed;
    private double ispeed;
    private String name;
    private int posX, posY;

    public abstract void setImagePosition(double x, double y);

    public abstract double getImagePositionX();
    public abstract double getImagePositionY();

    public abstract int getRow();

    public abstract int getColumn();

    public abstract int getHealth();

    public abstract void setHealth(int health);

    public abstract int getPower();
    public abstract int getPosX();
    public abstract int getPosY();

    public abstract double getISpeed();

    public abstract void setSpeed(double speed);

    public abstract void setColumn (int column);

    public abstract void step();

    public abstract void removeImage();

    public abstract String getName();

}
