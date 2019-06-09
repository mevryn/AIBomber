package pl.dszi.GeneticAlgorithm;

import pl.dszi.Booster.Booster;
import pl.dszi.Booster.BoosterType;

import java.util.Random;

public class Population {
    Booster[] Boosters;
    public Population(int populationSize, boolean start){
        Boosters = new Booster[populationSize];
        if(start){
            for(int i = 0; i < populationSize; i++){
                Random random = new Random();
                BoosterType type = BoosterType.BOOSTER_NOTYPE;
                while(type == BoosterType.BOOSTER_NOTYPE){
                    type = BoosterType.values()[random.nextInt(BoosterType.values().length)];
                }
                Boosters[i] = new Booster(random.nextInt() % 3, type, random.nextInt() % 10000);
            }
        }
    }

    public Booster GetBooster(int index){
        return Boosters[index];
    }

    public Booster GetFittest() {
        Booster fittest = Boosters[0];
        int index = 0;
        for (int i = 0; i < Boosters.length; i++) {
            if (fittest.GetFitness() <= GetBooster(i).GetFitness()) {
                fittest = GetBooster(i);
                index = i;
            }
        }
        return fittest;
    }

    public int GetPopulationLength(){return Boosters.length;}

    public void SaveBooster(int index, Booster booster){
        Boosters[index] = booster;
    }
}
