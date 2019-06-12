package pl.dszi.board;

import pl.dszi.engine.constant.Constant;

import java.awt.*;
import java.util.*;
import java.util.List;

public class BoardGameInfo {
    private Cell[][] cells;
    private final List<Point> startingPoints;

    BoardGameInfo(Cell[][] cells) {
        startingPoints = new ArrayList<>();
        initalizeStartingPoints();
        this.cells = cells;
    }


    private void initalizeStartingPoints() {
        startingPoints.add(new Point(0, 0));
        startingPoints.add(new Point(0, 1));
        startingPoints.add(new Point(0, 2));
        startingPoints.add(new Point(1, 0));
        startingPoints.add(new Point(2, 0));

        startingPoints.add(new Point(Constant.DEFAULT_GAME_TILES_HORIZONTALLY - 1, 0));
        startingPoints.add(new Point(Constant.DEFAULT_GAME_TILES_HORIZONTALLY - 2, 0));
        startingPoints.add(new Point(Constant.DEFAULT_GAME_TILES_HORIZONTALLY - 3, 0));
        startingPoints.add(new Point(Constant.DEFAULT_GAME_TILES_HORIZONTALLY - 1, 1));
        startingPoints.add(new Point(Constant.DEFAULT_GAME_TILES_HORIZONTALLY - 1, 2));

        startingPoints.add(new Point(0, Constant.DEFAULT_GAME_TILES_VERTICALLY - 1));
        startingPoints.add(new Point(0, Constant.DEFAULT_GAME_TILES_VERTICALLY - 2));
        startingPoints.add(new Point(0, Constant.DEFAULT_GAME_TILES_VERTICALLY - 3));
        startingPoints.add(new Point(1, Constant.DEFAULT_GAME_TILES_VERTICALLY - 1));
        startingPoints.add(new Point(2, Constant.DEFAULT_GAME_TILES_VERTICALLY - 1));

        startingPoints.add(new Point(Constant.DEFAULT_GAME_TILES_HORIZONTALLY - 1, Constant.DEFAULT_GAME_TILES_VERTICALLY - 1));
        startingPoints.add(new Point(Constant.DEFAULT_GAME_TILES_HORIZONTALLY - 1, Constant.DEFAULT_GAME_TILES_VERTICALLY - 2));
        startingPoints.add(new Point(Constant.DEFAULT_GAME_TILES_HORIZONTALLY - 1, Constant.DEFAULT_GAME_TILES_VERTICALLY - 3));
        startingPoints.add(new Point(Constant.DEFAULT_GAME_TILES_HORIZONTALLY - 2, Constant.DEFAULT_GAME_TILES_VERTICALLY - 1));
        startingPoints.add(new Point(Constant.DEFAULT_GAME_TILES_HORIZONTALLY - 3, Constant.DEFAULT_GAME_TILES_VERTICALLY - 1));
    }

    public Cell[][] getCells() {
        return cells;
    }

    boolean checkIfIsNotStartingPoint(Point point) {
        return startingPoints.stream().noneMatch(point1 -> point1.equals(point));
    }

    public Set<Cell> getAllBombs() {
        Set<Cell> bombs = new HashSet<>();
        for (Cell[] columns : cells) {
            for (Cell cell : columns) {
                if (cell.getType() == CellType.CELL_BOMB)
                    bombs.add(cell);
            }
        }
        return bombs;
    }

    public void setAllCellsToEmpty() {
        for (Cell[] columns : cells) {
            for (Cell cell : columns) {
                if (cell.getType() == CellType.CELL_CRATE)
                    cell.setType(CellType.CELL_EMPTY);
            }
        }
    }

    Set<Cell> getAllExplosions() {
        Set<Cell> exploCells = new HashSet<>();
        Arrays.stream(this.getCells()).forEach(cells -> Arrays.stream(cells).filter(cell -> cell.getType() == CellType.CELL_BOOM_CENTER).forEach(exploCells::add));
        return exploCells;
    }

}
