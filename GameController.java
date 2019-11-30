package game;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javafx.util.Duration;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.UIDefaults.ActiveValue;

class GameController implements EventHandler<KeyEvent>,Serializable {
    final private double FRAMES_PER_SECOND = 60.0;
    int difficulty;
    Player player;
    Enemy enemy;
    private boolean playerWin = false;
    private boolean zombieWin = false;
    transient static ArrayList<String> saveNameList = new ArrayList<String>();

    static boolean canBuyPlant[] = new boolean[4];
    static int timeElapsedLastBuy[] = new int[4];

    transient private Stage stage;
    transient static Timer timer;
    private boolean activeLawnm[] = new boolean[5];
    private long timeElapsed = 0;
    private Long secElapsed = new Long("0");
    private long sunDrop = 0;

    public GameController(int difficulty, Player player, Enemy enemy, Stage initStage) {
        this.difficulty = difficulty;
        this.player = player;
        this.enemy = enemy;
        this.stage = initStage;
        this.activeLawnm = player.getActiveLawn();
        for(int i=0;i<4;i++){
            canBuyPlant[i] = true;
            timeElapsedLastBuy[i] = 0;
        }
    }

    public void setStage(Stage initS){
        this.stage = initS;
    }

    public void startTimer() {
        this.timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        //System.out.println(MainMenu.playerName);
                        
                        for(int i=0;i<4;i++){
                            if(canBuyPlant[i]==false){
                                timeElapsedLastBuy[i]++;
                                if(timeElapsedLastBuy[i] >= 5000){
                                    canBuyPlant[i] = true;
                                       timeElapsedLastBuy[i] = 0;
                                }
                            }
                        }

                        updateAnimation();
                        timeElapsed++;
                        sunDrop++;
                    }
                });
            }
        };

        long frameTimeInMilliseconds = (long) (1);
        this.timer.schedule(timerTask, 0, frameTimeInMilliseconds);
    }

    public Player getPlayer() {
        return player;
    }

    private void updateAnimation() {
       // System.out.println("TIMEERRRRR :  " + secElapsed);

        if(sunDrop > 15000 ){
            System.out.println("Adding Sun");
            player.sunList.add(new Sun(player.root,player));
            sunDrop = 0;
        }

        if(timeElapsed > 1000){
            secElapsed++;
            timeElapsed = 0;
        }


        ArrayList<Plant> listOfPlants = player.getPlants();
        ArrayList<Zombie> listOfZombies = enemy.getZombies();
        ArrayList<Pea> listOfPeas = player.getPeas();
        ArrayList<Lawnmower> lawnmowers = player.getLawnmowers();

        GameWindow.timerLabel.setText("Zombies left: "+ ((Integer)listOfZombies.size()).toString());

        runFight(listOfPlants, listOfZombies, lawnmowers, listOfPeas);

        for (Pea pea : listOfPeas) {
            pea.step();
        }

        for (Zombie zombie : enemy.getZombies()) {
            zombie.step();
            //System.out.println("yello " + zombie.getImagePositionX());
        }

        for (Plant plant : listOfPlants) {
            plant.step();
        }

        for (Lawnmower lmv : lawnmowers) {
            if (lmv.getPresent() && activeLawnm[lmv.getRow() - 1]) {
                lmv.step();
            }
        }
    }

    private void runFight(ArrayList<Plant> plants, ArrayList<Zombie> zombies, ArrayList<Lawnmower> lawnmowers,
            ArrayList<Pea> peas) {

        boolean plantDie = false;

        // this is the list containing the zombies blocked by plants
        ArrayList<Zombie> blockZombie = new ArrayList<Zombie>();
        // System.out.println("---------------------------------------------");
        boolean delCB = false;
        boolean delZombie;


        for(Iterator<Plant> cb = plants.iterator(); cb.hasNext(); ){
            Plant a = cb.next();
            if(a.getName().equals("cherrybomb")){
                for(Iterator<Zombie> zb = zombies.iterator();zb.hasNext();){
                    Zombie xx = zb.next();
                    if(Math.abs(xx.getImagePositionX()-a.getPosX()) <= 20 && Math.abs(xx.getRow()-a.getRow())<=1){
                        xx.removeImage();
                        zb.remove();
                        delCB = true;
                    }
                }
                
                if(delCB){
                    a.removeImage();
                    cb.remove();
                    delCB = false;
                }
            }
        }

        

        for (Iterator<Zombie> iterator2 = zombies.iterator(); iterator2.hasNext();) {
            delZombie = false;
            Zombie zombie = iterator2.next();

            for (Lawnmower lmv : lawnmowers) {
                //System.out.println("LMV active: " + activeLawnm[lmv.getRow()-1]  + "  zomb " + zombie.getImagePositionX());
                if (activeLawnm[lmv.getRow() - 1] && lmv.getPresent() && lmv.getRow()==zombie.getRow()&& lmv.getImagePositionX() >= zombie.getImagePositionX()) {
                    zombie.removeImage();
                    //System.out.println("LV: " + lmv.getRow() + "  ");
                    zombie.removeImage();
                    try{
                        iterator2.remove();
                    }catch(Exception e){}
                        //break;
                }

                if (lmv.getImagePositionX() >= 800) {
                    lmv.removeImage();
                    activeLawnm[lmv.getRow() - 1] = false;
                    lmv.setPresent(false);
                }
            }

            //System.out.println("Zombie PosX: " + zombie.getImagePositionX());
            if (zombie.getImagePositionX() < 150) {
                if (lawnmowers.get(zombie.getRow() - 1).getPresent()) {
                    // lawnmowers.get(zombie.getRow()-1).setPresent(false);
                    //System.out.println(zombie.getImagePositionX() + "  : imgx z   row : " + zombie.getRow());
                    activeLawnm[zombie.getRow() - 1] = true;
                    zombie.removeImage();
                    iterator2.remove();
                    //delZombie = true;
                } else {
                    this.zombieWin = true;
                }
            }

            for (Iterator<Plant> iterator = plants.iterator(); iterator.hasNext();) {
                Plant plant = iterator.next();
                int plantRow = plant.getRow();
                int plantColumn = plant.getColumn();
                int zombieRow = zombie.getRow();
                int zombieColumn = zombie.getColumn();

                // if lawnmover is killing the zombie

                // if the plant is a peashooter, we should consider the pea hits the zombie
                if (plant.getName().equals("peashooter")) {
                    for (Iterator<Pea> iterator3 = peas.iterator(); iterator3.hasNext();) {
                        Pea pea = iterator3.next();
                        int peaRow = pea.getRow();
                        int peaX = (int) Math.round(pea.getImagePositionX());

                        //System.out.println("peaX : " + peaX + "   zom : " + zombie.getImagePositionX());
                        //System.out.println("peaRow : " + peaRow + "   zom : " + zombieRow + "   pl :" + plantRow);


                        if (plantRow == peaRow && peaRow == zombieRow && peaX >= (int) zombie.getImagePositionX()) {
                            //System.out.println("INNNNNN");
                            pea.removeImage();
                            int zombieHealth = zombie.getHealth();
                            int plantPower = plant.getPower();
                            zombie.setHealth(zombieHealth - plantPower);
                            pea.stopSound();
                            iterator3.remove();
                            if (zombie.getHealth() <= 0) {
                                zombie.removeImage();
                                iterator2.remove();
                            }
                        }

                        // remove the pea if it is out of the image
                        if (pea.getImagePositionX() > 1000) {
                            iterator3.remove();
                        }
                    }
                }

                // if the zombie is blocked, it should eat the plant
                if (plantRow == zombieRow && plantColumn == zombieColumn) {
                    zombie.setSpeed(0);
                    blockZombie.add(zombie);
                    int plantHealth = plant.getHealth();
                    int zombiePower = zombie.getPower();
                    plant.setHealth(plantHealth - zombiePower);
                    if (plant.getHealth() < 50) {
                        if (plant.getHealth() <= 0) {
                            plantDie = true;
                        }
                        zombie.setSpeed(zombie.getISpeed());
                    }
                }
                if (plantDie == true) {
                    plant.removeImage();
                    iterator.remove();
                }
                plantDie = false;
            }

        }
        // If player has killed all zombie in the list, show message that player has
        // won.
        if (enemy.getZombies().isEmpty()) {
            this.playerWin = true;
            System.out.println("Player has Won");
        }
        checkEnd();
    }

    /** check if we win the game */
    public void checkEnd() {
        if (this.playerWin) {
            String result = "You win the Game";
            System.out.println(result);
            Popup gameWinPopUp = new Popup();
            Text winText = new Text("You have won the round! New Plant Unlocked in next Level");
            gameWinPopUp.getContent().add(winText);
            gameWinPopUp.show(MainMenu.window);
            //winText.setFont(new Font("Ariel",14));
            PauseTransition wait = new PauseTransition(Duration.seconds(10));
            wait.setOnFinished((e) -> {
                /*YOUR METHOD*/
                gameWinPopUp.hide();
                //btn.setDisable(false);
                wait.playFromStart();
            });
            wait.play();
            try {
                timer.cancel();
                MainMenu.window.setScene(MainMenu.menu);
            } catch (Exception e) {
            }
        }
        if (this.zombieWin){
            String result = "You lose the Game";
            System.out.println(result);
            
            Popup gameWinPopUp = new Popup();
            Text winText = new Text("You have Lost the round! Zombies Ate You");
            gameWinPopUp.getContent().add(winText);
            gameWinPopUp.show(MainMenu.window);
            //winText.setFont(new Font("Ariel",14));
            PauseTransition wait = new PauseTransition(Duration.seconds(10));
            wait.setOnFinished((e) -> {
                /*YOUR METHOD*/
                gameWinPopUp.hide();
                //btn.setDisable(false);
                wait.playFromStart();
            });
            wait.play();

            try{
                timer.cancel();
                MainMenu.window.setScene(MainMenu.menu);
            } catch (NullPointerException e){}
            catch(Exception e){}

        }
    }

    public void setup(){

        MainMenu.playerName = player.playerName;

        ArrayList<Zombie> zomb = new ArrayList<Zombie>();
        ArrayList<Pea> pea = new ArrayList<Pea>();
        ArrayList<Plant> plant = new ArrayList<Plant>();
        ArrayList<Lawnmower> lwm = new ArrayList<Lawnmower>();
        ArrayList<Sun> sunl = new ArrayList<Sun>();
        
        for(Iterator<Lawnmower> ll = player.getLawnmowers().iterator(); ll.hasNext();){
            Lawnmower lw = ll.next(); 
            lw = new Lawnmower(lw.getRow(), player.root, lw.getPresent());
            lwm.add(lw);
        }

        for(Iterator<Plant> zz = player.getPlants().iterator(); zz.hasNext();){
            Plant p = zz.next();
            if(p.getName().equals("sunflower")){
                p = new Sunflower(p.getRow(),p.getColumn(),p.getPosX(),p.getPosY(),player.root,player);
            }
            if(p.getName().equals("peashooter")){
                p = new Peashooter(p.getRow(),p.getColumn(),p.getPosX(),p.getPosY(),player.root,player);
            }
            if(p.getName().equals("wallnut")){
                p = new WallNut(p.getRow(),p.getColumn(),p.getPosX(),p.getPosY(),player.root,player);
            }
            if(p.getName().equals("cherrybomb")){
                p = new CherryBomb(p.getRow(),p.getColumn(),p.getPosX(),p.getPosY(),player.root,player);                
            }
            plant.add(p);
        }


        for(Iterator<Zombie> zz = enemy.getZombies().iterator(); zz.hasNext();){
            Zombie zm = zz.next();
            System.out.println("yelloh " + zm.getImagePositionX());
            if(zm.getName().equals("normalZ")){
                System.out.println("Normal Z " + zm.getPosX());
                zm = new NormalZombie(zm.getRow(), zm.getColumn(),(int)zm.getImagePositionX(),(int)zm.getImagePositionY(),player.root);       
            }
            if(zm.getName().equals("strongZ")){
                zm = new StrongZombie(zm.getRow(), zm.getColumn(), (int)zm.getImagePositionX(),(int)zm.getImagePositionY(),player.root);       
            }
            System.out.println("setup imgx : " + zm.getImagePositionX());
            zomb.add(zm);
        }

        for(Iterator<Sun> zz = player.sunList.iterator(); zz.hasNext();){
            Sun zm = zz.next();
            zm = new Sun(player.root,player);
            sunl.add(zm);
        }


        for(Iterator<Pea> zz = player.getPeas().iterator(); zz.hasNext();){
            Pea pp = zz.next();
            pp = new Pea(pp.getRow(),pp.getColumn(),pp.getPosX(),pp.getPosY(),player.root,player);
            pea.add(pp);
        }

        enemy.setZombieList(zomb);
        player.setPlantList(plant);
        player.setLawnmowerList(lwm);
        player.setPeaList(pea);
        player.sunList = sunl;

    }

    @Override
    public void handle(KeyEvent keyEvent) {

    }

}
