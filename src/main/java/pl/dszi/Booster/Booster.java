package pl.dszi.Booster;

import pl.dszi.player.Player;

import java.util.Timer;
import java.util.TimerTask;

public class Booster {
    private int value;
    private BoosterType type;
    private Player player;
    private Timer boostTimer;
    private int timerDelay;
    private int fitness;



    public Booster(int value, BoosterType type, int timer){
        this.value = value;
        this.type = type;
        boostTimer = new Timer();
        if(timer > 0) {
            timerDelay = timer;
        }
        else{
            timerDelay = 1000;
        }
        calculateFitness();
    }


    public int getValue(){
        return value;
    }
    public BoosterType getBoosterType() { return type; }
    public Player getPlayer() { return player; }
    public Timer getBoostTimer() { return boostTimer; }
    public int getTimerDelay() { return timerDelay; }

    public void setValue(int value){
        this.value = value;
    }

    public void setType(BoosterType type){
        this.type = type;
    }

    public void setPlayer(Player player){
        this.player = player;
        starting();
    }

    public void setTimerDelay(int timer){
        if(timer > 0)
        {
            timerDelay = timer;
            boostTimer = new Timer();
        }
        else {
            timerDelay = 1000;
            boostTimer = new Timer();
        }
    }


    public int GetFitness(){
        calculateFitness();
        return fitness;
    }

    private void calculateFitness(){
        fitness = value * timerDelay;
        switch(type){
            case BOOSTER_IMMORTALITY:
                fitness *= 0.19;
                break;
            case BOOSTER_EXPLOSION:
                fitness *= 0.2;
                break;
            case BOOSTER_HP:
                fitness *= 0.21;
                break;
            case BOOSTER_MORE_BOMBS:
                fitness *= 0.22;
                break;
            case BOOSTER_NO_TYPE:
                fitness *= 0;
                break;
        }
    }

    private void starting(){
        switch(type){
            case BOOSTER_EXPLOSION:
                player.setBombRange(player.getBombRange() + value);
                break;
            case BOOSTER_HP:
                if(player.getCurrentHp() < player.getMaxHp() && value >= 0) {
                    player.setCurrentHp(player.getCurrentHp() + value);
                }
                break;
            case BOOSTER_IMMORTALITY:
                player.setMortality(false);
                break;
            case BOOSTER_MORE_BOMBS:
                player.setBombAmount(player.getBombAmount() + value);
                break;
            default:
                break;
        }
        boostTimer.schedule(new TimerTask(){ public void run(){ Finished();}}, timerDelay);
    }

    private void Finished(){
        switch(type){
            case BOOSTER_IMMORTALITY:
                player.setMortality(true);
                break;
            case BOOSTER_EXPLOSION:
                player.setBombRange(player.getBombRange() - value);
                break;
            case BOOSTER_MORE_BOMBS:
                player.setBombAmount(player.getBombAmount() - value);
                break;
            default:
                break;
        }
    }
}
