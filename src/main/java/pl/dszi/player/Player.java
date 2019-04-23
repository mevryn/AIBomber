package pl.dszi.player;

public class Player {

    private final int maxHp;
    private int currentHp;
    private final int  range;
    public Player(int maxHp) {
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.range = 3;
    }
}
