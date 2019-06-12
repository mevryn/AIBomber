package pl.dszi.board;

class Parents {
    private final Integer firstPopulation;
    private final Integer secondPopulation;

    Integer getFirstPopulation() {
        return firstPopulation;
    }

    Integer getSecondPopulation() {
        return secondPopulation;
    }

    Parents(Integer firstPopulation, Integer secondPopulation) {

        this.firstPopulation = firstPopulation;
        this.secondPopulation = secondPopulation;
    }
}
