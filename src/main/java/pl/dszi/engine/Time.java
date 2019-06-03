package pl.dszi.engine;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Time {

    public static void scheduleTimer(Runnable task, int delay) {
        ScheduledExecutorService scheduler
                = Executors.newSingleThreadScheduledExecutor();

        scheduler.schedule(task, delay, TimeUnit.MILLISECONDS);
        scheduler.shutdown();
    }
}
