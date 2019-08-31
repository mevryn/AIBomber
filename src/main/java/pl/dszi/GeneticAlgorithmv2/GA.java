package pl.dszi.GeneticAlgorithmv2;

import pl.dszi.Booster.Booster;
import pl.dszi.Booster.BoosterPopulation;
import pl.dszi.Booster.BoosterType;
import pl.dszi.engine.constant.Constant;

public class GA {
    public static Population evolvePopulation(Population population){
        Population newPopulation = new Population(population.GetPopulationLength(), population.GetBoosterPopulation(0).GetPopulationWidth(), false);
        if(Constant.GA_ELITISM){
            newPopulation.SaveBoosterPopulation(0, population.GetFittest());
        }
        int elitismOffset = Constant.GA_ELITISM ? 1 : 0;
        for(int i = elitismOffset; i < population.GetPopulationLength(); i++){
            BoosterPopulation individual1 = tournamentSelection(population);
            BoosterPopulation individual2 = tournamentSelection(population);
            BoosterPopulation newIndividual = crossover(individual1, individual2);
            newPopulation.SaveBoosterPopulation(i, newIndividual);
        }
        for(int i = elitismOffset; i < population.GetPopulationLength(); i++){
            newPopulation.SaveBoosterPopulation(i, mutate(newPopulation.GetBoosterPopulation(i)));
        }
        return newPopulation;
    }

    private static BoosterPopulation mutate(BoosterPopulation boosterPopulation) {
        BoosterPopulation newIndividual = new BoosterPopulation(boosterPopulation.GetPopulationWidth());
        return crossover(boosterPopulation, newIndividual);
    }

    private static BoosterPopulation crossover(BoosterPopulation individual1, BoosterPopulation individual2) {
        BoosterPopulation newIndividual = new BoosterPopulation(individual1.GetPopulationWidth(), true);
        if(individual1.GetPopulationWidth() == individual2.GetPopulationWidth()){
            for(int i = 0; i < individual1.GetPopulationWidth(); i++){
                Booster newIndividualBooster = new Booster(0, BoosterType.BOOSTER_NO_TYPE,0);
                Booster boosterIndividual1 = null;
                Booster boosterIndividual2 = null;
                if(Constant.GA_REVERSE_CROSSOVER) {
                    boosterIndividual1 = individual1.GetBoosterAt(i);
                    boosterIndividual2 = individual2.GetBoosterAt(individual2.GetPopulationWidth() - i - 1);
                }else{
                    boosterIndividual1 = individual1.GetBoosterAt(i);
                    boosterIndividual2 = individual2.GetBoosterAt(i);
                }
                    if (Math.random() <= Constant.GA_UNIFORM_RATE) {
                        newIndividualBooster.setTimerDelay(boosterIndividual1.getTimerDelay());
                    } else {
                        newIndividualBooster.setTimerDelay(boosterIndividual2.getTimerDelay());
                    }
                    if (Math.random() <= Constant.GA_UNIFORM_RATE) {
                        newIndividualBooster.setValue(boosterIndividual1.getValue());
                    } else {
                        newIndividualBooster.setValue(boosterIndividual2.getValue());
                    }
                    if (Math.random() <= Constant.GA_UNIFORM_RATE) {
                        newIndividualBooster.setType(boosterIndividual1.getBoosterType());
                    } else {
                        newIndividualBooster.setType(boosterIndividual2.getBoosterType());
                    }
                newIndividual.SetBoosterAt(i, newIndividualBooster);
            }
        }
        return newIndividual;
    }

    private static BoosterPopulation tournamentSelection(Population population) {
        Population tournament = new Population(Constant.GA_TOURNAMENT_SIZE,population.GetPopulationWidth(), false);
        for(int i = 0; i < Constant.GA_TOURNAMENT_SIZE; i++){
            int randomId = (int) (Math.random() * population.GetPopulationWidth());
            tournament.SaveBoosterPopulation(i, population.GetBoosterPopulation(randomId));
        }
        return tournament.GetFittest();
    }
}