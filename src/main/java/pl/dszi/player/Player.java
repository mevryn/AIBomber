package pl.dszi.player;

import pl.dszi.engine.GameStatus;
import pl.dszi.engine.Time;
import pl.dszi.engine.constant.Constant;
import pl.dszi.player.noob.AIController;

import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

    private final AIController AIController;

    public String getName() {
        return name;
    }

    public AIController getAIController() {
        return AIController;
    }

    public Player(String name, int maxHp) {
        this.name = name;
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.color = new Color((int) (Math.random() * 0x1000000));
        this.bombAmount = 1;
        this.range = 3;
        AIController = null;
    }


    public Player(String name, int maxHp, AIController AIController) {
        this.name = name;
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.color = new Color((int) (Math.random() * 0x1000000));
        this.bombAmount = 1;
        this.range = Constant.DEFAULT_EXPLOSION_RANGE;
        this.AIController = AIController;
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

    public void damagePlayer(GameStatus gameStatus) {
        if (mortal || gameStatus == GameStatus.TESTING) {
            currentHp--;
            mortal = false;
            Time.scheduleTimer(() -> mortal = true, Constant.IMMORTALITY_TIMER);
            makeMortalAgain();
        }
    }

    public boolean canPlantBomb() {
        return bombActuallyTicking < bombAmount;
    }

    public boolean getIfManual() {
        return this.AIController == null;
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
