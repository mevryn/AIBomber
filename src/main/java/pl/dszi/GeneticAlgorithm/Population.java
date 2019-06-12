package pl.dszi.GeneticAlgorithm;

import pl.dszi.Booster.Booster;
import pl.dszi.Booster.BoosterType;

import java.util.Random;

public class Population {
    private Booster[] Boosters;
    public Population(int populationSize, boolean start){
        Boosters = new Booster[populationSize];
        if(start){
            for(int i = 0; i < populationSize; i++){
                Random random = new Random();
                BoosterType type = BoosterType.BOOSTER_NO_TYPE;
                while(type == BoosterType.BOOSTER_NO_TYPE){
                    type = BoosterType.values()[random.nextInt(BoosterType.values().length)];
                }
                Boosters[i] = new Booster(random.nextInt() % 3, type, random.nextInt() % 10000);
            }
        }
    }

    Booster getBooster(int index){
        return Boosters[index];
    }

    public Booster getFittest() {
        Booster fittest = Boosters[0];
        for (int i = 0; i < Boosters.length; i++) {
            if (fittest.GetFitness() <= getBooster(i).GetFitness()) {
                fittest = getBooster(i);
            }
        }
        return fittest;
    }

    int GetPopulationLength(){return Boosters.length;}

    void SaveBooster(int index, Booster booster){
        Boosters[index] = booster;
    }
}
