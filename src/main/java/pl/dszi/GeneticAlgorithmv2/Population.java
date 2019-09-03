package pl.dszi.GeneticAlgorithmv2;

import pl.dszi.Booster.Booster;
import pl.dszi.Booster.BoosterPopulation;
import pl.dszi.Booster.BoosterType;

import java.util.Random;

public class Population {
    private BoosterPopulation[] BoostersPopulations;
    private int PopulationSize = 0;
    private int PopulationWidth = 0;

    public int GetPopulationSize(){
        return PopulationSize;
    }
    public int GetPopulationWidth(){
        return PopulationWidth;
    }

    public Population(int populationSize, int populationWidth, boolean start){
        PopulationSize = populationSize;
        PopulationWidth = populationWidth;
            BoostersPopulations = new BoosterPopulation[populationWidth];
            if(start){
                for(int i = 0; i < populationWidth; i++){
                    BoostersPopulations[i] = new BoosterPopulation(populationSize);
                }
            }
    }

    public BoosterPopulation GetBoosterPopulation(int index){
        return BoostersPopulations[index];
    }

    public BoosterPopulation GetFittest(){
        BoosterPopulation fittest = BoostersPopulations[0];
        for(int i = 0; i < PopulationSize; i++){
            if(fittest.GetFitness() <= BoostersPopulations[i].GetFitness()){
                fittest = GetBoosterPopulation(i);
            }
        }
        return fittest;
    }

    public Booster GetFittestBooster(){
        BoosterPopulation fittestPopulation = GetFittest();
        Booster fittestBooster = fittestPopulation.GetBoosterAt(0);
        for(int i = 0; i < fittestPopulation.GetPopulationWidth(); i++){
            if(fittestBooster.GetFitness() <= fittestPopulation.GetBoosterAt(i).GetFitness()){
                fittestBooster = fittestPopulation.GetBoosterAt(i);
            }
        }
        return fittestBooster;
    }

    public int GetTotalFitness(){
        int totalFitness = 0;
        for(BoosterPopulation boosterPopulation : BoostersPopulations){
            totalFitness += boosterPopulation.GetFitness();
        }
        return totalFitness;
    }

    int GetPopulationLength(){
        return BoostersPopulations.length;
    }

    void SaveBoosterPopulation(int index, BoosterPopulation boosterPopulation) {
        BoostersPopulations[index] = boosterPopulation;
    }
}
