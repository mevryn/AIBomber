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
        //faworyzowanie osobnika je�li GA_ELITISM == true
        if(Constant.GA_ELITISM){
            newPopulation.SaveBoosterPopulation(0, population.GetFittest());
        }
        int elitismOffset = Constant.GA_ELITISM ? 1 : 0;
        for(int i = elitismOffset; i < population.GetPopulationLength(); i++){
        	//wybieramy 2 osobniki do "turnieju" i przechodzimy do krzy�owania wybranych 2 osobnik�w
            BoosterPopulation individual1 = tournamentSelection(population);
            BoosterPopulation individual2 = tournamentSelection(population);
            BoosterPopulation newIndividual = crossover(individual1, individual2);
            //dodajemy do tymczasowej populacji nowego osobnika po krzyzowaniu
            newPopulation.SaveBoosterPopulation(i, newIndividual);
        }
        //mutacja osobnik�w
        for(int i = elitismOffset; i < population.GetPopulationLength(); i++){
            newPopulation.SaveBoosterPopulation(i, mutate(newPopulation.GetBoosterPopulation(i)));
        }
        return newPopulation;
    }
    //losowany jest kompletnie nowy osobnik i krzy�owany z wcze�niej wykrzy�owanym osobnikiem w tymczasowej populacji
    private static BoosterPopulation mutate(BoosterPopulation boosterPopulation) {
        BoosterPopulation newIndividual = new BoosterPopulation(boosterPopulation.GetPopulationWidth());
        return crossover(boosterPopulation, newIndividual);
    }

    private static BoosterPopulation crossover(BoosterPopulation individual1, BoosterPopulation individual2) {
    	//utworzenie nowej pustej tymczasowej populacji
        BoosterPopulation newIndividual = new BoosterPopulation(individual1.GetPopulationWidth(), true);
        //sprawdzenie czy populacje s� jednakowej d�ugo�ci
        if(individual1.GetPopulationWidth() == individual2.GetPopulationWidth()){
        	//od 0 do maksymalnej d�ugo�ci populacji
            for(int i = 0; i < individual1.GetPopulationWidth(); i++){
            	//tymczasowy nowy booster, kt�ry p�zniej zostanie wype�niony i zapisany
                Booster newIndividualBooster = new Booster(0, BoosterType.BOOSTER_NO_TYPE,0);
                Booster boosterIndividual1 = null;
                Booster boosterIndividual2 = null;
                //wybieranie booster�w do krzy�owania na podstawie ustawie� gry
                if(Constant.GA_REVERSE_CROSSOVER) {
                	//je�eli GA_REVERSE_CROSSOVER = true, to wtedy boostery krzy�uj� si� 04,13,22,31,40
                    boosterIndividual1 = individual1.GetBoosterAt(i);
                    boosterIndividual2 = individual2.GetBoosterAt(individual2.GetPopulationWidth() - i - 1);
                }else{
                	////je�eli GA_REVERSE_CROSSOVER = true, to wtedy boostery krzy�uj� si� 00,11,22,33,44
                    boosterIndividual1 = individual1.GetBoosterAt(i);
                    boosterIndividual2 = individual2.GetBoosterAt(i);
                }
                //je�li losowana liczba jest mniejsza/r�wna ni� GA_UNIFORM_RATE(0.5) to korzystamy z w�a�ciwo�ci pierwszego wybranego boostera, je�li jest wi�ksza to z drugiego
                    if (Math.random() <= Constant.GA_UNIFORM_RATE) {
                    	//czas trwania booster�w takich jak: wi�kszy zasi�g eksplozji/nie�miertelno��/wi�ksza ilo�c bomb
                        newIndividualBooster.setTimerDelay(boosterIndividual1.getTimerDelay());
                    } else {
                        newIndividualBooster.setTimerDelay(boosterIndividual2.getTimerDelay());
                    }
                    if (Math.random() <= Constant.GA_UNIFORM_RATE) {
                    	//warto�� booster�w, 0, 1 lub 2 (np je�li warto�� 1 to wylosowany booster odnawia jedno zdrowie/zwi�szka ilo�c bomb o 1/zwi�ksza zasi�g o 1)
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
                    //nowemu osobnikowi ustawiamy booster na pozycji "i" o warto�ciach "newIndividualBooster"
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