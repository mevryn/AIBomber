package pl.dszi.GeneticAlgorithmv2;

import pl.dszi.Booster.Booster;
import pl.dszi.Booster.BoosterPopulation;
import pl.dszi.Booster.BoosterType;
import pl.dszi.engine.constant.Constant;

public class GA {
	//ewolucja populacji
    public static Population evolvePopulation(Population population){
    	//tworzona jest tymczasowa nowa populacja na podstawie wielkosci poprzedniej populacji
        Population newPopulation = new Population(population.GetPopulationLength(), population.GetBoosterPopulation(0).GetPopulationWidth(), false);
        //faworyzowanie osobnika jeœli GA_ELITISM == true
        if(Constant.GA_ELITISM){
            newPopulation.SaveBoosterPopulation(0, population.GetFittest());
        }
        int elitismOffset = Constant.GA_ELITISM ? 1 : 0;
        for(int i = elitismOffset; i < population.GetPopulationLength(); i++){
        	//wybieramy 2 osobników u¿ywaj¹c zasady turniejowej i przechodzimy do krzy¿owania wybranych 2 osobników
            BoosterPopulation individual1 = tournamentSelection(population);
            BoosterPopulation individual2 = tournamentSelection(population);
            BoosterPopulation newIndividual = crossover(individual1, individual2);
            //dodajemy do tymczasowej populacji nowe osobniki uzyskane po krzyzowaniu
            newPopulation.SaveBoosterPopulation(i, newIndividual);
        }
        //mutacja osobników
        for(int i = elitismOffset; i < population.GetPopulationLength(); i++){
            newPopulation.SaveBoosterPopulation(i, mutate(newPopulation.GetBoosterPopulation(i)));
        }
        return newPopulation;
    }
    //losowany jest kompletnie nowy osobnik i krzy¿owany z wczeœniej wykrzy¿owanym osobnikiem w tymczasowej populacji
    private static BoosterPopulation mutate(BoosterPopulation boosterPopulation) {
        BoosterPopulation newIndividual = new BoosterPopulation(boosterPopulation.GetPopulationWidth());
        return crossover(boosterPopulation, newIndividual);
    }

    private static BoosterPopulation crossover(BoosterPopulation individual1, BoosterPopulation individual2) {
    	//utworzenie nowej pustej tymczasowej populacji
        BoosterPopulation newIndividual = new BoosterPopulation(individual1.GetPopulationWidth(), true);
        //sprawdzenie czy populacje s¹ jednakowej d³ugoœci
        if(individual1.GetPopulationWidth() == individual2.GetPopulationWidth()){
        	//od 0 do maksymalnej d³ugoœci populacji
            for(int i = 0; i < individual1.GetPopulationWidth(); i++){
            	//tymczasowy nowy booster, który pózniej zostanie wype³niony i zapisany
                Booster newIndividualBooster = new Booster(0, BoosterType.BOOSTER_NO_TYPE,0);
                Booster boosterIndividual1 = null;
                Booster boosterIndividual2 = null;
                //wybieranie boosterów do krzy¿owania na podstawie ustawieñ gry
                if(Constant.GA_REVERSE_CROSSOVER) {
                	//je¿eli GA_REVERSE_CROSSOVER = true, to wtedy boostery krzy¿uj¹ siê 0-4,1-3,2-2,3-1,4-0
                    boosterIndividual1 = individual1.GetBoosterAt(i);
                    boosterIndividual2 = individual2.GetBoosterAt(individual2.GetPopulationWidth() - i - 1);
                }else{
                	////je¿eli GA_REVERSE_CROSSOVER = true, to wtedy boostery krzy¿uj¹ siê 0-0,1-1,2-2,3-3,4-4
                    boosterIndividual1 = individual1.GetBoosterAt(i);
                    boosterIndividual2 = individual2.GetBoosterAt(i);
                }
                //jeœli losowana liczba jest mniejsza/równa ni¿ GA_UNIFORM_RATE(0.5) to korzystamy z w³aœciwoœci pierwszego wybranego boostera, jeœli jest wiêksza to z drugiego
                    if (Math.random() <= Constant.GA_UNIFORM_RATE) {
                    	//czas trwania boosterów takich jak: wiêkszy zasiêg eksplozji/nieœmiertelnoœæ/wiêksza iloœæ bomb
                        newIndividualBooster.setTimerDelay(boosterIndividual1.getTimerDelay());
                    } else {
                        newIndividualBooster.setTimerDelay(boosterIndividual2.getTimerDelay());
                    }
                    if (Math.random() <= Constant.GA_UNIFORM_RATE) {
                    	//wartoœæ boosterów, 0, 1 lub 2 (np jeœli wartoœæ 1 to wylosowany booster odnawia jedno zdrowie/zwiêszka iloœc bomb o 1/zwiêksza zasiêg o 1)
                        newIndividualBooster.setValue(boosterIndividual1.getValue());
                    } else {
                        newIndividualBooster.setValue(boosterIndividual2.getValue());
                    }
                    if (Math.random() <= Constant.GA_UNIFORM_RATE) {
                    	//typ boostera ( BOOSTER_EXPLOSION,BOOSTER_HP,BOOSTER_IMMORTALITY,BOOSTER_MORE_BOMBS )
                        newIndividualBooster.setType(boosterIndividual1.getBoosterType());
                    } else {
                        newIndividualBooster.setType(boosterIndividual2.getBoosterType());
                    }
                    //nowemu osobnikowi ustawiamy booster na pozycji "i" o wartoœciach "newIndividualBooster"
                newIndividual.SetBoosterAt(i, newIndividualBooster);
            }
        }
        return newIndividual;
    }
    //wybranie jednego osobnika z populacji
    private static BoosterPopulation tournamentSelection(Population population) {
    	//tymczasowa populacja na czas turnieju o wielkosci GA_TOURNAMENT_SIZE(2)
        Population tournament = new Population(Constant.GA_TOURNAMENT_SIZE,population.GetPopulationWidth(), false);
        for(int i = 0; i < Constant.GA_TOURNAMENT_SIZE; i++){
        	//wybrany losowo osobnik jest dodany do tymczasowej populacji
            int randomId = (int) (Math.random() * population.GetPopulationWidth());
            tournament.SaveBoosterPopulation(i, population.GetBoosterPopulation(randomId));
        }
        //zwracamy lepszego osobnika z 2 wybranych w turnieju
        return tournament.GetFittest();
    }
}