package pl.dszi.player;

import pl.dszi.engine.constant.Constants;

import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Player {
    private final String name;
    private final Color color;
    private final int maxHp;
    private int currentHp;
    private final int range;
    private int bombAmount;
    private int bombActualyTicking=0;
    private boolean mortal = true;
    private boolean insideBomb = false;
    private boolean alive = true;

    public void setAlive() {
        alive = currentHp > 0;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isInsideBomb() {

        return insideBomb;
    }

    public void setInsideBomb(boolean insideBomb) {
        this.insideBomb = insideBomb;
    }

    public int getRange() {
        return range;
    }

    public int getBombAmount() {
        return bombAmount;
    }

    public int getBombActualyTicking() {
        return bombActualyTicking;
    }

    public void plantBomb(){
        bombActualyTicking++;
        this.setInsideBomb(true);
    }
    private void detonateBomb(){
        bombActualyTicking--;
    }
    private final PlayerController playerController;

    public String getName() {
        return name;
    }

    public PlayerController getPlayerController() {
        return playerController;
    }

    public Player(String name, int maxHp, PlayerController playerController) {
        this.name = name;
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.color = new Color((int) (Math.random() * 0x1000000));
        this.bombAmount = 2;
        this.range = 3;
        this.playerController = playerController;
        this.playerController.setPlayer(this);
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public boolean isMortal() {
        return mortal;
    }

    private void makeMortalAgain(){
        ScheduledExecutorService scheduler
                = Executors.newSingleThreadScheduledExecutor();

        Runnable task = new Runnable() {
            public void run() {
                mortal = true;
            }
        };

        int delay = Constants.IMMORTALITY_TIMER;
        scheduler.schedule(task, delay, TimeUnit.SECONDS);
        scheduler.shutdown();
    }
    public void restoreBombAmountEvent(){
        ScheduledExecutorService scheduler
                = Executors.newSingleThreadScheduledExecutor();

        Runnable task = new Runnable() {
            public void run() {detonateBomb();
            }
        };

        int delay = Constants.BASIC_BOMB_EXPLOSION_TIMER;
        scheduler.schedule(task, delay, TimeUnit.SECONDS);
        scheduler.shutdown();
    }

    public void damagePlayer(){
        currentHp--;
        System.out.println(currentHp);
        mortal=false;
        makeMortalAgain();
    }
    public boolean getIfManual(){
       return this.playerController.checkIfManual();
    }
    public Color getColor() {
        return color;
    }
}
