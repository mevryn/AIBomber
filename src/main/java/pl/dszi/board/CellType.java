package pl.dszi.board;

import pl.dszi.engine.constant.Constants;
import pl.dszi.player.Player;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public enum CellType {
    CELL_EMPTY(' ',true),
    CELL_WALL('#',false),
    CELL_CRATE('c',false),
    CELL_BOMB('b',true),
    CELL_BOOM_LEFT('<',false),
    CELL_BOOM_RIGHT('>',false),
    CELL_BOOM_CENTER('+',true),
    CELL_BOOM_VERTICAL('|',false),
    CELL_BOOM_HORIZONTAL('-',false);

    public final char code;

    public boolean destroyable;

   public boolean walkable;
   private Player bombPlayerOwner;

    public Player getBombPlayerOwner() {
        return bombPlayerOwner;
    }

    public boolean isDestroyable() {
        return destroyable;
    }

    public void setBombPlayerOwner(Player bombPlayerOwner) {

        this.bombPlayerOwner = bombPlayerOwner;
    }

    private CellType(char code, boolean walkable) {
        this.code = code;
        this.walkable = walkable;
        destroyable = code == 'c';
    }

}
