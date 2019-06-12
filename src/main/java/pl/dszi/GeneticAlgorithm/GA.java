package pl.dszi.GeneticAlgorithm;

import pl.dszi.Booster.Booster;
import pl.dszi.Booster.BoosterType;
import pl.dszi.engine.constant.Constant;

import java.util.Random;

public class GA {
    public static Population evolvePopulation(Population population){
        Population newPopulation = new Population(population.GetPopulationLength(), false);
        if(Constant.GA_ELITISM){
            newPopulation.SaveBooster(0, population.getFittest());
        }

        int elitismOffset = Constant.GA_ELITISM ? 1 : 0;
        for(int i = elitismOffset; i < population.GetPopulationLength(); i++){
            Booster individual1 = TournamentSelection(population);
            Booster individual2 = TournamentSelection(population);
            Booster newIndividual = Crossover(individual1,individual2);
            newPopulation.SaveBooster(i, newIndividual);
        }
        for(int i = elitismOffset; i < population.GetPopulationLength(); i++){
            newPopulation.SaveBooster(i,Mutate(newPopulation.getBooster(i)));
        }
        return newPopulation;
    }

    private static Booster TournamentSelection(Population population) {
        Population tournament = new Population(Constant.GA_TOURNAMENT_SIZE, false);
        for (int i = 0; i < Constant.GA_TOURNAMENT_SIZE; i++) {
            int randomId = (int) (Math.random() * population.GetPopulationLength());
            tournament.SaveBooster(i, population.getBooster(randomId));
        }
        return tournament.getFittest();
    }

    private static Booster Crossover(Booster individual1, Booster individual2){
        Booster newIndividual = new Booster(0, BoosterType.BOOSTER_NO_TYPE,0);
        if(Math.random() <= Constant.GA_UNIFORM_RATE){
            newIndividual.setTimerDelay(individual1.getTimerDelay());
        }else{
            newIndividual.setTimerDelay(individual2.getTimerDelay());
        }
        if(Math.random() <= Constant.GA_UNIFORM_RATE){
            newIndividual.setValue(individual1.getValue());
        }else{
            newIndividual.setValue(individual2.getValue());
        }
        if(Math.random() <= Constant.GA_UNIFORM_RATE){
            newIndividual.setType(individual1.getBoosterType());
        }else{
            newIndividual.setType(individual2.getBoosterType());
        }
        return newIndividual;
    }

    private static Booster Mutate(Booster booster){
        Booster newIndividual = new Booster(0, booster.getBoosterType(), 0);
        Random random = new Random();
        newIndividual.setValue(Math.random() >= 0.5 ? booster.getValue() + random.nextInt() % 2 : booster.getValue() - random.nextInt() % 2);
        newIndividual.setTimerDelay(Math.random() >= 0.5 ? booster.getTimerDelay() + random.nextInt() % 1000 : booster.getTimerDelay() - random.nextInt() % 1000);
        return newIndividual;
    }
}
