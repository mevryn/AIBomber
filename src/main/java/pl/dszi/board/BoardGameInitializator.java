package pl.dszi.board;

import java.awt.*;
import java.util.Random;

class BoardGameInitializator {
    static void initializeBoard(BoardGameInfo boardGameInfo) {
        boolean even = true;
        for (int i = 0; i < boardGameInfo.getCells().length; i++) {
            for (int j = 0; j < boardGameInfo.getCells()[i].length; j++) {
                boardGameInfo.getCells()[i][j] = new Cell(CellType.CELL_EMPTY, new Point(i, j));
                if (even)
                    even = false;
                else
                    even = true;
            }
        }
        for (int i = 1; i < boardGameInfo.getCells().length - 1; i = i + 2) {
            for (int j = 1; j < boardGameInfo.getCells()[i].length - 1; j = j + 2) {
                boardGameInfo.getCells()[i][j] = new Cell(CellType.CELL_WALL, new Point(i, j));
            }
        }
         randomizeCrateCells(boardGameInfo.getCells(),boardGameInfo);
    }

    private static void randomizeCrateCells(Cell[][] cells, BoardGameInfo boardGameInfo) {
        Random random = new Random();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (random.nextBoolean() && cells[i][j].getType() != CellType.CELL_WALL && !boardGameInfo.checkIfIsNotStartingPoint(new Point(i, j))) {
                    cells[i][j] = new Cell(CellType.CELL_CRATE, new Point(i, j));
                }
            }
        }
    }
}
