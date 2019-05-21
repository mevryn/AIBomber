package pl.dszi.board;

import pl.dszi.engine.Constants;

import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExplosionCell extends Cell {

    private boolean burning;

    public ExplosionCell(CellType type, Point point, int indexX, int indexY) {
        super(type, point, indexX, indexY);
        burning=true;
        this.extinguishAfterTime();
    }
    public ExplosionCell(Cell cell){
        super(cell.type, cell.point, cell.indexX, cell.indexY);
        burning=true;
        this.extinguishAfterTime();
    }

    public boolean isBurning() {
        return burning;
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
