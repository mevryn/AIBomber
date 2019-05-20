package pl.dszi.player;

import java.awt.*;

public class Player {
    private final String name;
    private final Color color;
    private final int maxHp;
    private int currentHp;
    private final int range;
    private int bombAmount;
    private int bombActualyTicking=0;
    public int getBombAmount() {
        return bombAmount;
    }

    public int getBombActualyTicking() {
        return bombActualyTicking;
    }

    public void plantBomb(){
        bombActualyTicking++;
    }
    public void detonateBomb(){
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
        this.bombAmount = 1;
        this.range = 3;
        this.playerController = playerController;
        this.playerController.setPlayer(this);
    }

    public boolean getIfManual(){
       return this.playerController.checkIfManual();
    }
    public Color getColor() {
        return color;
    }
}
