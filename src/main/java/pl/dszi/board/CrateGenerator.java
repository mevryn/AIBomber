package pl.dszi.board;

import pl.dszi.engine.constant.Constant;

import java.awt.*;
import java.util.*;
import java.util.List;

class CrateGenerator {

    private int numberOfCrates;
    private List<int[]> listOfPopulations = new ArrayList<>();
    private Map<Integer, Integer> populationScores = new HashMap<>();


    private int[] bestGen;

    CrateGenerator(int numberOfCrates) {
        this.numberOfCrates = numberOfCrates;
        if (this.numberOfCrates > Constant.MAXIMUM_CRATE_AMOUNT) {
            this.numberOfCrates = new Random().nextInt(93);
        }
        randomizeInitialPopulation();
        initializePopulationScores();
    }

    private void initializePopulationScores() {
        populationScores.put(0, 0);
        populationScores.put(1, 0);
        populationScores.put(2, 0);
        populationScores.put(3, 0);
        populationScores.put(4, 0);
        populationScores.put(5, 0);
        populationScores.put(6, 0);
        populationScores.put(7, 0);
        populationScores.put(8, 0);
        populationScores.put(9, 0);
    }

    int[] getBestGen() {
        return bestGen;
    }

    int[] returnRandomCrates() {
        int[] crates = new int[numberOfCrates];
        for (int i = 0; i < crates.length; i++) {
            crates[i] = new Random().nextInt(Constant.MAXIMUM_CRATE_AMOUNT);
        }
        return crates;
    }

    private void randomizeInitialPopulation() {
        initPopList();
        initPopList();
        for (int[] cratesPopulation : listOfPopulations) {
            for (int i = 0; i < cratesPopulation.length; i++) {
                cratesPopulation[i] = new Random().nextInt(Constant.MAXIMUM_CRATE_AMOUNT);
            }
            Arrays.sort(cratesPopulation);
        }

    }

    private void initPopList() {
        listOfPopulations.add(new int[numberOfCrates]);
        listOfPopulations.add(new int[numberOfCrates]);
        listOfPopulations.add(new int[numberOfCrates]);
        listOfPopulations.add(new int[numberOfCrates]);
        listOfPopulations.add(new int[numberOfCrates]);
    }

    void randomizeCrateCells(Cell[][] cells, BoardGameInfo boardGameInfo, int populationIndex) {
        int BoosterCrates = 0;
        int[] population = listOfPopulations.get(populationIndex);
        for (int i = 0; i < numberOfCrates; i++) {
            int col = population[i] % Constant.DEFAULT_GAME_TILES_HORIZONTALLY;
            int row = population[i] / Constant.DEFAULT_GAME_TILES_HORIZONTALLY;
            boolean generated = false;
            while (!generated) {
                if (boardGameInfo.checkIfIsNotStartingPoint(new Point(col, row)) && cells[col][row].getType() == CellType.CELL_EMPTY) {
                    BoosterCrates = getBoosterCrates(cells, BoosterCrates, col, row);
                    generated = true;
                } else {
                    population[i] = new Random().nextInt(Constant.MAXIMUM_CRATE_AMOUNT);
                    col = population[i] % Constant.DEFAULT_GAME_TILES_HORIZONTALLY;
                    row = population[i] / Constant.DEFAULT_GAME_TILES_HORIZONTALLY;
                }
            }
        }
    }

    private int getBoosterCrates(Cell[][] cells, int boosterCrates, int col, int row) {
        if (new Random().nextInt() % 101 >= Constant.BOOSTER_CRATE_THRESHOLD && boosterCrates < Constant.MAX_BOOSTER_CRATES) {
            boosterCrates++;
            cells[col][row].setType(CellType.CELL_CRATEBONUS);
        } else {
            cells[col][row].setType(CellType.CELL_CRATE);
        }
        return boosterCrates;
    }

    void createCrates(Cell[][] cells, int[] crates) {
        int BoosterCrates = 0;
        for (int crate : crates) {
            int col = crate % Constant.DEFAULT_GAME_TILES_HORIZONTALLY;
            int row = crate / Constant.DEFAULT_GAME_TILES_HORIZONTALLY;
            BoosterCrates = getBoosterCrates(cells, BoosterCrates, col, row);
        }
    }

    void setPopulationScore(int population, int actionAmount) {
        populationScores.replace(population, actionAmount);
    }

    Parents makeSelectionWithRanking() {
        List<Map.Entry<Integer, Integer>> populationAndItScore = new ArrayList<>(populationScores.entrySet());
        populationAndItScore.sort((o1, o2) -> o2.getValue() - o1.getValue());
        bestGen = listOfPopulations.get(populationAndItScore.get(0).getKey());
        return new Parents(populationAndItScore.get(0).getKey(), populationAndItScore.get(1).getKey());
    }

    void reproduction(Parents parents) {
        crossing(parents);
        mutation();
    }

    private void crossing(Parents parents) {
        List<Integer> croppingIndexes = new ArrayList<>();
        for (int i = 0; i < listOfPopulations.size(); i++) {
            boolean randomized = false;
            while (!randomized) {
                int randomIndex = new Random().nextInt(numberOfCrates);
                if (!croppingIndexes.contains(randomIndex)) {
                    croppingIndexes.add(randomIndex);
                    randomized = true;
                }
            }
        }
        int[] firstParent = listOfPopulations.get(parents.getFirstPopulation());
        int[] secondParent = listOfPopulations.get(parents.getSecondPopulation());
        listOfPopulations.set(0, firstParent);
        listOfPopulations.set(1, secondParent);
        for (int iterator = 2; iterator < listOfPopulations.size() - 1; iterator++) {
            listOfPopulations.set(iterator, cropPopulation(parents, croppingIndexes.get(iterator)));
        }

    }

    private int[] cropPopulation(Parents parents, int index) {
        int[] population = new int[numberOfCrates];
        for (int i = 0; i < numberOfCrates - 1; i++) {
            if (i < index)
                population[i] = listOfPopulations.get(parents.getFirstPopulation())[i];
            else {
                population[i] = listOfPopulations.get(parents.getSecondPopulation())[i];
            }
        }
        return population;
    }

    private void mutation() {
        if (new Random().nextInt(100) == 99) {
            listOfPopulations.get(new Random().nextInt(10))[new Random().nextInt(numberOfCrates)] = new Random().nextInt(Constant.MAXIMUM_CRATE_AMOUNT);
        }
    }
}
