package game;

import java.io.Serializable;
import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class Player implements Serializable{
    private int sun = 200;
    String playerName;
    private ArrayList<Plant> listPlants = new ArrayList<Plant>();
    private ArrayList<Pea> listPeas = new ArrayList<Pea>();
    private ArrayList<Lawnmower> lawnmowers = new ArrayList<Lawnmower>();
    ArrayList<Sun> sunList = new ArrayList<Sun>(); 
    private boolean activeLawnm[] = new boolean[5];
    Enemy enemy;
    transient Pane root = new Pane();
    transient Label sunOriginal;
    transient Label sunLabel;

    public Player(int difficulty,Pane root, Label priceLabel){
        this.sunOriginal = priceLabel;
        this.root = root;
        for(int i=0;i<5;i++){
            lawnmowers.add(new Lawnmower(i+1,root,true));
        }
        for (int i = 0; i < 5; i++) {
            activeLawnm[i] = false;
        }
        this.enemy = new Enemy(root, difficulty);

    }

    public void setPane(Pane root){
        this.root = root;
    }

    public boolean[] getActiveLawn(){
        return activeLawnm;
    }

    public int getSun(){
        return this.sun;
    }

    public ArrayList<Plant> getPlants(){
        return listPlants;
    }

    public ArrayList<Pea> getPeas(){
        return listPeas;
    }

    public ArrayList<Lawnmower> getLawnmowers(){
        return lawnmowers;
    }

    public void setPlantList(ArrayList<Plant> pp){
        this.listPlants = pp;
    }

    public void setPeaList(ArrayList<Pea> pp){
        this.listPeas = pp;
    }

    public void setLawnmowerList(ArrayList<Lawnmower> pp){
        this.lawnmowers = pp;
    }

    public void setSun(int sun){
        this.sun = sun;
        this.root.getChildren().remove(this.sunOriginal);
        this.root.getChildren().remove(this.sunLabel);
        Label sunL = new Label(Integer.toString(getSun()));
        sunLabel = sunL;
        sunL.setFont(new Font(20));
        this.root.getChildren().add(sunLabel);
        sunL.setTranslateX(20);
        sunL.setTranslateY(63);
    }

    public void addPlants(Plant plant){
        listPlants.add(plant);
    }

    public void addPea(Pea pea){
        listPeas.add(pea);
    }
}
