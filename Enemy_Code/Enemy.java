package game;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.layout.Pane;

import java.io.Serializable;
import java.util.*;

public class Enemy implements Serializable{
    private int difficulty;
    private ArrayList<Zombie> zombies = new ArrayList<Zombie>();
    transient Pane root = new Pane();
    transient Random rand = new Random();


    public Enemy(Pane root, int difficulty){
        System.out.println("ENEmy");
        this.root = root;
        this.difficulty = difficulty;
        zombies = generateZombies(difficulty);
    }

    public void setPane(Pane r){
        this.root = r;
    }

    public void setZombieList(ArrayList<Zombie> zom){
        this.zombies = zom;
    }

    public ArrayList<Zombie> generateZombies(int difficulty){
        int norm = (int) Math.round(difficulty * 0.6);
        System.out.println(norm);
        int strong = difficulty - norm;
        System.out.println("difficulty: "+difficulty);
        for (int i = 0; i < difficulty; i++){

            for (int j = 0; j < norm; j++){
                int r = rand.nextInt(5)  + 1;
                int c = rand.nextInt(5)*3 - 3;
                NormalZombie normalZombie =  new NormalZombie(r, c, Math.max(800,205 + (c-1)*40), 60 + (r-1)*90 , root);
                zombies.add(normalZombie);
            }
            for (int k = 0; k < strong; k++){
                int r = rand.nextInt(5)  + 1;
                int c = rand.nextInt(5)*3 - 3;
                StrongZombie strongZombie =  new StrongZombie(r, c, Math.max(800,205 + (c-1)*40), 60 + (r-1)*90 , root);
                zombies.add(strongZombie);
            }
        }

        return zombies;
    }

    public ArrayList<Zombie> getZombies(){
        return zombies;
    }

    boolean rowHasZombie(int r){
        for(Zombie zom : this.zombies){
            if(zom.getRow()==r)return true;
        }
        return false;
    }

}
