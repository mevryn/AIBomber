package pl.dszi.Booster;

import pl.dszi.player.Player;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class Booster {
    private int Value;
    private BoosterType Type;
    private Player Player;
    private Timer BoostTimer;
    private int TimerDelay;
    private int Fitness;

    public int GetValue(){
        return Value;
    }
    public BoosterType GetBoosterType() { return Type; }
    public Player GetPlayer() { return Player; }
    public Timer GetBoostTimer() { return BoostTimer; }
    public int GetTimerDelay() { return TimerDelay; }

    public void SetValue(int value){
        Value = value;
    }

    public void SetType(BoosterType type){
        Type = type;
    }

    public void SetPlayer(Player player){
        Player = player;
        Starting();
    }

    public void SetTimerDelay(int timer){
        if(timer > 0)
        {
            TimerDelay = timer;
            BoostTimer = new Timer();
        }
        else {
            TimerDelay = 1000;
            BoostTimer = new Timer();
        }
    }

    public Booster(int value, BoosterType type, int timer){
        Value = value;
        Type = type;
        BoostTimer = new Timer();
        if(timer > 0) {
            TimerDelay = timer;
        }
        else{
            TimerDelay = 1000;
        }
        CalculateFitness();
    }

    public int GetFitness(){
        CalculateFitness();
        return Fitness;
    }

    private void CalculateFitness(){
        Fitness = Value * TimerDelay;
        //switch(Type){
        //    case BOOSTER_IMMORTALITY:
        //        Fitness *= 0.19;
        //        break;
        //    case BOOSTER_EXPLOSION:
        //        Fitness *= 0.2;
        //        break;
        //    case BOOSTER_HP:
        //        Fitness *= 0.21;
        //        break;
        //    case BOOSTER_MOREBOMBS:
        //        Fitness *= 0.22;
        //        break;
        //    case BOOSTER_NOTYPE:
        //        Fitness *= 0;
        //        break;
        //}
    }

    public void Starting(){
        switch(Type){
            case BOOSTER_EXPLOSION:
                Player.setBombRange(Player.getBombRange() + Value);
                break;
            case BOOSTER_HP:
                if(Player.getCurrentHp() < Player.getMaxHp() && Value >= 0) {
                    Player.setCurrentHp(Player.getCurrentHp() + Value);
                }
                break;
            case BOOSTER_IMMORTALITY:
                Player.setMortality(false);
                break;
            case BOOSTER_MOREBOMBS:
                Player.setBombAmount(Player.getBombAmount() + Value);
                break;
            default:
                break;
        }
        BoostTimer.schedule(new TimerTask(){ public void run(){ Finished();}}, TimerDelay);
    }

    public void Finished(){
        switch(Type){
            case BOOSTER_IMMORTALITY:
                Player.setMortality(true);
                break;
            case BOOSTER_EXPLOSION:
                Player.setBombRange(Player.getBombRange() - Value);
                break;
            case BOOSTER_MOREBOMBS:
                Player.setBombAmount(Player.getBombAmount() - Value);
                break;
            default:
                break;
        }
    }
}
