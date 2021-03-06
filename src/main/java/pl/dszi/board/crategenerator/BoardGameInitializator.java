package pl.dszi.board.crategenerator;

import pl.dszi.board.BoardGameInfo;
import pl.dszi.board.Cell;
import pl.dszi.board.CellType;

import java.awt.*;

public class BoardGameInitializator {
    public static void initializeBoard(BoardGameInfo boardGameInfo) {

        for (int i = 0; i < boardGameInfo.getCells().length; i++) {
            for (int j = 0; j < boardGameInfo.getCells()[i].length; j++) {
                boardGameInfo.getCells()[i][j] = new Cell(CellType.CELL_EMPTY, new Point(i, j));
            }
        }
        for (int i = 1; i < boardGameInfo.getCells().length - 1; i = i + 2) {
            for (int j = 1; j < boardGameInfo.getCells()[i].length - 1; j = j + 2) {
                boardGameInfo.getCells()[i][j] = new Cell(CellType.CELL_WALL, new Point(i, j));
            }
        }
    }
}
