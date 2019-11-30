package game;

import java.util.Iterator;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import javafx.scene.Group;

public class SunController implements EventHandler<MouseEvent> {
    Player player;
    Pane root;
    Label sun;
    Label sunOriginal;

    public SunController(Player player, Pane root) {
        this.root = root;
        this.player = player;
        this.sunOriginal = GameWindow.sun1;
    }

    @Override
    public void handle(MouseEvent event) {
       
        Pair<Integer,Integer> aa = MainMenu.getCell((int)event.getX(), (int)event.getY());
        int row = aa.getKey();
        int column = aa.getValue();

        for (Plant plant : this.player.getPlants()) {
            if ("sunflower".equals(plant.getName())) {
                if (row == plant.getRow() && column == plant.getColumn()) {
                    Sunflower s = (Sunflower) plant;
                    if (s.isHasSun()){
                        s.setHasSun(false);
                        s.removeSun();
                        this.root.getChildren().remove(sunOriginal);
                        if(this.sun != null){
                            this.root.getChildren().remove(this.sun);
                            player.setSun(player.getSun() + 50);
                        } else {
                            player.setSun(player.getSun() + 50);
                        }
                    }

                }

            }
        }

        System.out.println("Checking for Dropped Suns");

        for(Iterator<Sun> s = player.sunList.iterator();s.hasNext();){
            Sun x = s.next();
            //System.out.println("Event: " + event.getX() + " " + event.getY());
            //System.out.println("Sun: " + x.getPosX() + " " + x.getPosY());
            // System.out.println("X: " + Math.abs(x.getPosX() - event.getX()));
            // System.out.println("Y: " + Math.abs(x.getPosY() - event.getY()));
            if( Math.abs(x.getPosX() - event.getX()) <= 50 && Math.abs(x.getPosY() - event.getY()) <= 50 ){
                System.out.println("Got one sun");
                x.removeSun();
                this.root.getChildren().remove(sunOriginal);
                if(this.sun != null){
                    this.root.getChildren().remove(this.sun);
                    player.setSun(player.getSun() + 50);
                } else {
                    player.setSun(player.getSun() + 50);
                }
                s.remove();
            }
        }

    }
}





