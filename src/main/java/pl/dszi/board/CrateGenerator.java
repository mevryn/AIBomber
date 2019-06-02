package pl.dszi.board;

import pl.dszi.engine.constant.Constants;

import java.awt.*;
import java.util.Random;

public class CrateGenerator {

    private int numberOfCrates;

    CrateGenerator(int numberOfCrates) {
        this.numberOfCrates=numberOfCrates;
        if(this.numberOfCrates> Constants.MAXIMUM_CRATE_AMOUNT){
            this.numberOfCrates=new Random().nextInt(93);
        }
    }

    private void RandomizeInitialPopulation(){
            int[] cratesPopulation1=new int[numberOfCrates];
            for(int i = 0;i<cratesPopulation1.length;i++){
                cratesPopulation1[i] = new Random().nextInt(Constants.MAXIMUM_CRATE_AMOUNT);
            }

    }
    void randomizeCrateCells(Cell[][] cells, BoardGameInfo boardGameInfo) {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (new Random().nextBoolean() && cells[i][j].getType() != CellType.CELL_WALL && !boardGameInfo.checkIfIsNotStartingPoint(new Point(i, j))) {
                    cells[i][j] = new Cell(CellType.CELL_CRATE, new Point(i, j));
                }
            }
        }
    }

}
