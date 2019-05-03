package pl.dszi.board;

import pl.dszi.engine.Constants;

import java.awt.*;

public class BoardGameInfo {
    final private Cell[][] cells;

    public BoardGameInfo(Cell[][] cells) {
        this.cells = cells;
        setBoard();
    }

    private void setBoard() {
        boolean even = true;
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                this.cells[i][j] = new Cell(CellType.CELL_EMPTY, new Point((i + 1) * Constants.DEFAULT_CELL_SIZE, (j + 1) * Constants.DEFAULT_CELL_SIZE), even, i, j);
                if (even)
                    even = false;
                else
                    even = true;
            }
        }
        for (int i = 1; i < cells.length - 1; i = i + 2) {
            for (int j = 1; j < cells[i].length - 1; j = j + 2) {
                this.cells[i][j] = new Cell(CellType.CELL_WALL, new Point((i + 1) * Constants.DEFAULT_CELL_SIZE, (j + 1) * Constants.DEFAULT_CELL_SIZE), i, j);
            }
        }
    }
    public Cell[][] getCells() {
        return cells;
    }
}
