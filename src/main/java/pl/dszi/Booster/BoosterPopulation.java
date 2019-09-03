package pl.dszi.Booster;

import java.util.Random;
//pojedynczy osobnik zawieraj¹cy boostery
public class BoosterPopulation {
    Booster[] boosters;
    public BoosterPopulation(int populationSize){
        boosters = new Booster[populationSize];
        for(int i = 0; i < populationSize; i++){
            Random random = new Random();
            BoosterType type = BoosterType.BOOSTER_NO_TYPE;
            while(type == BoosterType.BOOSTER_NO_TYPE){
                type = BoosterType.values()[random.nextInt(BoosterType.values().length)];
            }
            boosters[i] = new Booster(random.nextInt() % 3, type, random.nextInt() % 10000);
        }
    }

    public BoosterPopulation(int populationSize, boolean empty){
        boosters = new Booster[populationSize];
        if(empty){
            return;
        }
        else{
            for(int i = 0; i < populationSize; i++){
                Random random = new Random();
                BoosterType type = BoosterType.BOOSTER_NO_TYPE;
                while(type == BoosterType.BOOSTER_NO_TYPE){
                    type = BoosterType.values()[random.nextInt() % BoosterType.values().length];
                }
                boosters[i] = new Booster(random.nextInt() % 3, type, random.nextInt() % 10000);
            }
        }
    }

    public int GetPopulationWidth(){
        return boosters.length;
    }

    public Booster GetBoosterAt(int index){
        return boosters[index];
    }

    public void SetBoosterAt(int index, Booster booster){
        boosters[index] = booster;
    }

    public double GetFitness(){
        double fitness = 0;
        //booster.length = d³ugoœæ tablicy osobnika(5)
        for(int i = 0; i < boosters.length; i++){
            fitness += boosters[i].GetFitness();
        }
        return fitness;
    }
}
