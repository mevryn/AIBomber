package pl.dszi.player;

import pl.dszi.engine.Time;
import pl.dszi.engine.constant.Constant;

import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import pl.dszi.player.noob.NoobPlayerController;

public class Player {
    private final String name;
    private final Color color;
    private final int maxHp;
    private int currentHp;
    private int range;
    private int bombAmount;
    private int bombActuallyTicking = 0;
    private boolean mortal = true;
    private boolean insideBomb = false;


    public boolean isAlive() {
        return currentHp > 0;
    }

    public boolean isInsideBomb() {

        return insideBomb;
    }

    public void setInsideBomb(boolean insideBomb) {
        this.insideBomb = insideBomb;
    }


    public int getBombAmount() {
        return bombAmount;
    }

    public int getBombActuallyTicking() {
        return bombActuallyTicking;
    }

    public void plantBomb() {
        bombActuallyTicking++;
        this.setInsideBomb(true);
    }

    private void detonateBomb() {
        bombActuallyTicking--;
    }

    private final NoobPlayerController noobPlayerController;

    public String getName() {
        return name;
    }

    public NoobPlayerController getNoobPlayerController() {
        return noobPlayerController;
    }

    public Player(String name, int maxHp) {
        this.name = name;
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.color = new Color((int) (Math.random() * 0x1000000));
        this.bombAmount = 1;
        this.range = 3;
        noobPlayerController = null;
    }


    public Player(String name, int maxHp, NoobPlayerController NoobPlayerController) {
        this.name = name;
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.color = new Color((int) (Math.random() * 0x1000000));
        this.bombAmount = 1;
        this.range = 3;
        this.noobPlayerController = NoobPlayerController;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public boolean isMortal() {
        return mortal;
    }

    private void makeMortalAgain() {
        ScheduledExecutorService scheduler
                = Executors.newSingleThreadScheduledExecutor();
        Runnable task = () -> mortal = true;
        int delay = Constant.IMMORTALITY_TIMER;
        scheduler.schedule(task, delay, TimeUnit.SECONDS);
        scheduler.shutdown();
    }


    public void restoreBombAmountEvent() {
        Time.scheduleTimer(this::detonateBomb, Constant.BASIC_BOMB_EXPLOSION_TIMER);
    }

    public void damagePlayer() {
        if (mortal) {
            currentHp--;
            System.out.println(currentHp);
            mortal = false;
            Time.scheduleTimer(() -> mortal = true, Constant.IMMORTALITY_TIMER);
            makeMortalAgain();
        }
    }

    public boolean canPlantBomb() {
        return bombActuallyTicking < bombAmount;
    }

    public boolean getIfManual() {
        return this.noobPlayerController == null;
    }

    public Color getColor() {
        return color;
    }

    public void setMortality(boolean mortal) {
        this.mortal = mortal;
    }

    public int getBombRange() {
        return range;
    }

    public void setBombRange(int range) {
        this.range = range;
    }

    public void setBombAmount(int amount) {
        bombAmount = amount;
    }

    public void setCurrentHp(int hp) {
        currentHp = hp;
    }

    public int getMaxHp() {
        return maxHp;
    }
}
