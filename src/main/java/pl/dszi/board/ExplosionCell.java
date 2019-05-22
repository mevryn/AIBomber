package pl.dszi.board;

import pl.dszi.engine.Constants;
import pl.dszi.player.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import java.util.List;

public class ExplosionCell extends Cell {

    private boolean burning;
    private List<Player> damagedPlayerList;
    public ExplosionCell(CellType type, Point point, int indexX, int indexY) {
        super(type, point, indexX, indexY);
        burning=true;
        damagedPlayerList = new ArrayList();
        this.extinguishAfterTime();
    }
    public ExplosionCell(Cell cell){
        super(cell.type, cell.point, cell.indexX, cell.indexY);
        burning=true;
        damagedPlayerList = new ArrayList();
        this.extinguishAfterTime();
    }

    public boolean isBurning() {
        return burning;
    }

    public void addAlreadyDamagedPlayer(Player player){
        damagedPlayerList.add(player);
    }

    public boolean checkIfPlayerWasAlreadyDamaged(Player player){
        if(damagedPlayerList!=null)
            return damagedPlayerList.contains(player);
            else return false;
    }

    private void extinguishAfterTime(){
        ScheduledExecutorService scheduler
                = Executors.newSingleThreadScheduledExecutor();

        Runnable task = new Runnable() {
            public void run() {
                burning = false;
            }
        };

        int delay = Constants.BASIC_BOMB_EXPLOSION_BURNING_TIMER;
        scheduler.schedule(task, delay, TimeUnit.SECONDS);
        scheduler.shutdown();
    }
}
