package pl.dszi.GeneticAlgorithm;

import pl.dszi.Booster.Booster;
import pl.dszi.Booster.BoosterType;
import pl.dszi.engine.constant.Constants;

import java.util.Random;

public class GA {
    public static Population EvolvePopulation(Population population){
        System.out.println("Ewoluowanie populacji");
        Population newPopulation = new Population(population.GetPopulationLength(), false);
        if(Constants.GA_ELITISM){
            newPopulation.SaveBooster(0, population.GetFittest());
        }

        int elitismOffset = Constants.GA_ELITISM ? 1 : 0;
        for(int i = elitismOffset; i < population.GetPopulationLength(); i++){
            Booster individual1 = TournamentSelection(population);
            Booster individual2 = TournamentSelection(population);
            Booster newIndividual = Crossover(individual1,individual2);
            newPopulation.SaveBooster(i, newIndividual);
        }
        for(int i = elitismOffset; i < population.GetPopulationLength(); i++){
            newPopulation.SaveBooster(i,Mutate(newPopulation.GetBooster(i)));
        }
        return newPopulation;
    }

    private static Booster TournamentSelection(Population population) {
        Population tournament = new Population(Constants.GA_TOURNAMENT_SIZE, false);
        for (int i = 0; i < Constants.GA_TOURNAMENT_SIZE; i++) {
            int randomId = (int) (Math.random() * population.GetPopulationLength());
            tournament.SaveBooster(i, population.GetBooster(randomId));
        }
        Booster fittest = tournament.GetFittest();
        return fittest;
    }

    private static Booster Crossover(Booster individual1, Booster individual2){
        Booster newIndividual = new Booster(0, BoosterType.BOOSTER_NOTYPE,0);
        if(Math.random() <= Constants.GA_UNIFORM_RATE){
            newIndividual.SetTimerDelay(individual1.GetTimerDelay());
        }else{
            newIndividual.SetTimerDelay(individual2.GetTimerDelay());
        }
        if(Math.random() <= Constants.GA_UNIFORM_RATE){
            newIndividual.SetValue(individual1.GetValue());
        }else{
            newIndividual.SetValue(individual2.GetValue());
        }
        if(Math.random() <= Constants.GA_UNIFORM_RATE){
            newIndividual.SetType(individual1.GetBoosterType());
        }else{
            newIndividual.SetType(individual2.GetBoosterType());
        }
        return newIndividual;
    }

    private static Booster Mutate(Booster booster){
        Booster newIndividual = new Booster(0, booster.GetBoosterType(), 0);
        Random random = new Random();
        newIndividual.SetValue(Math.random() >= 0.5 ? booster.GetValue() + random.nextInt() % 2 : booster.GetValue() - random.nextInt() % 2);
        newIndividual.SetTimerDelay(Math.random() >= 0.5 ? booster.GetTimerDelay() + random.nextInt() % 1000 : booster.GetTimerDelay() - random.nextInt() % 1000);
        return newIndividual;
    }
}
