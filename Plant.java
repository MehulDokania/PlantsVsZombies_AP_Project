package game;

import java.io.Serializable;

public abstract class Plant implements Serializable{


    public abstract void setPosition(int x, int y);
    
    public abstract int getPrice();
    public abstract int getPosX();
    public abstract int getPosY();
    public abstract int getColumn();

    public abstract String getName();
    public abstract int getHealth();
    

    public abstract void setHealth(int health);
    
    public abstract void step();

    public abstract void removeImage();

    
    public abstract int getRow();

    public abstract int getPower();
    

    public abstract void setPower(int power);
    




}
