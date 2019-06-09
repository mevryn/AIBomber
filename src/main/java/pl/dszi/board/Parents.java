package pl.dszi.board;

public class Parents {
    private final Integer firstPopulation;
    private final Integer secondPopulation;

    public Integer getFirstPopulation() {
        return firstPopulation;
    }

    public Integer getSecondPopulation() {
        return secondPopulation;
    }

    public Parents(Integer firstPopulation, Integer secondPopulation) {

        this.firstPopulation = firstPopulation;
        this.secondPopulation = secondPopulation;
    }
}
