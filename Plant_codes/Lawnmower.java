package game;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

class Lawnmower implements Serializable {

    private double velocityX = 0.3;
    transient private Image lawnmv;
    private int row;
    transient private StackPane s = new StackPane();
    transient private Pane root = new Pane();
    private boolean present;
    private double imageY;
    private double imageX;
    transient private ImageView lmiv;
    
    public Lawnmower(int row, Pane root, boolean present) {

        try{
            this.lawnmv = new Image(Files.newInputStream(Paths.get("src/res/lawn-mower.png")));
        }catch(Exception e){
            System.out.println("Error loading lawnmower img");
        }

        this.present = present;
        this.row = row;
        this.lmiv = new ImageView(lawnmv);
        this.root = root;
        s.getChildren().add(lmiv);
        root.getChildren().add(s);
        s.setTranslateY(110 + row*92 - 80);
        s.setTranslateX(138);
        this.imageX = 138;
        this.imageY = 110 + row*92 - 80;

        if(!present){
            removeImage();
        }

    }
    
    boolean getPresent(){
        return this.present;
    }

    void setPresent(boolean b){
        this.present = b;
    }

    int getRow(){
        return this.row;
    }

    public void removeImage(){
        this.s.getChildren().remove(this.lmiv);
        this.root.getChildren().remove(this.s);
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

    public void showImage(){
        s.getChildren().add(lmiv);
        root.getChildren().add(s);
    }

}
