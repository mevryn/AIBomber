package pl.dszi.board;

import pl.dszi.engine.Constants;
import pl.dszi.player.Player;

import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BombCell extends Cell {

    private Player player;
    private boolean playerInside;

    public boolean isPlayerInside() {
        return playerInside;
    }

    public void setPlayerInside(boolean playerInside) {
        this.playerInside = playerInside;
    }

    public BombCell(Point point, Player player) {
        super(CellType.CELL_BOMB, point);
        this.player = player;
        this.playerInside=true;
        this.ExplodeAfterTime();
    }

    BombCell(Cell cell, Player player){
        super(CellType.CELL_BOMB, cell.getPoint());
        this.player = player;
        this.playerInside =true;
        this.ExplodeAfterTime();
    }

    void ExplodeAfterTime(){
        ScheduledExecutorService scheduler
                = Executors.newSingleThreadScheduledExecutor();

        Runnable task = new Runnable() {
            public void run() {

            }
        };

        int delay = Constants.BASIC_BOMB_EXPLOSION_TIMER;
        scheduler.schedule(task, delay, TimeUnit.SECONDS);
        scheduler.shutdown();
    }
    public Player getPlayer() {
        return player;
    }
}
