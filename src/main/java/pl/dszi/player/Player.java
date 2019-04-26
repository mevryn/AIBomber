package pl.dszi.player;

import java.awt.*;

public class Player {
    private final String name;
    private final Color color;
    private final int maxHp;
    private int currentHp;
    private final int range;
    private final PlayerController playerController;

    public String getName() {
        return name;
    }

    public Player(String name, int maxHp, PlayerController playerController) {
        this.name = name;
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.color = new Color((int) (Math.random() * 0x1000000));

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
