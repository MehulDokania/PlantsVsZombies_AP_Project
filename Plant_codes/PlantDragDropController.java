package game;

import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

public class PlantDragDropController implements EventHandler<DragEvent> {
    Pane root;
    Player player;

    public PlantDragDropController(Pane root, Player player){
        this.root = root;
        this.player = player;
    }
    
    @Override
    public void handle(DragEvent event) {
        String type = event.getDragboard().getString();
        int row=1;
        int column=(int)event.getX()-40;

        System.out.println(event.getX() + "  " + event.getY());

        int sX = 205;
        int eX = 770;
        int difX = (eX-sX)/9; 

        int sY = 110;
        int eY = 580;
        int difY = (eY-sY)/5;

        Pair<Integer,Integer> aa = MainMenu.getCell((int)event.getX(), (int)event.getY());

        row = aa.getKey();
        column = aa.getValue();

        if(row !=-1 && column != -1){
                
                boolean add = true;

                int posX = sX + (column-1)*difX;// - 55;
                int posY = sY + (row-1)*difY ;//- 80;
                System.out.println("ROW/C " + row + "  " + column);
                System.out.println("ADDED " + posX + "  " + posY);


                //check for collision
                for (Plant plant : player.getPlants()) {
                    if (plant.getRow() == row && plant.getColumn() == column) {
                        add = false;
                    }
                }

                if (add) {
                    switch (type) {
                        case "peashooter":
                            if (this.player.getSun() >= 100 && GameController.canBuyPlant[0]){
                                GameController.canBuyPlant[0] = false;
                                Peashooter peashooter = new Peashooter(row, column, posX, posY,root, player);
                                player.addPlants(peashooter);
                                player.setSun(player.getSun() - peashooter.getPrice());
                            }

                            break;
                        case "wallnut":
                            if (this.player.getSun() >= 50 && GameController.canBuyPlant[1] && MainMenu.difficulty > 1) {
                                GameController.canBuyPlant[1] = false;
                                WallNut wallnut = new WallNut(row, column, posX, posY,root,player);
                                player.addPlants(wallnut);
                                player.setSun(player.getSun() - wallnut.getPrice());
                            }
                            break;
                        case "sunflower":

                            if (this.player.getSun() >= 50 && GameController.canBuyPlant[2]) {
                                GameController.canBuyPlant[2] = false;
                                Sunflower sunflower = new Sunflower(row, column, posX, posY, root,player);
                                player.addPlants(sunflower);
                                player.setSun(player.getSun() - sunflower.getPrice());
                            }   
                            break;

                        case "cherrybomb":
                            if (this.player.getSun() >= 150 && GameController.canBuyPlant[3] && MainMenu.difficulty > 2) {
                                GameController.canBuyPlant[3] = false;
                                CherryBomb cherrybomb = new CherryBomb(row, column, posX, posY, root,player);
                                player.addPlants(cherrybomb);
                                player.setSun(player.getSun() - cherrybomb.getPrice());

                            }
                            break;
                    }
                }
            
        }
    }

}

