package pl.dszi.board;

import pl.dszi.engine.constant.Constants;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BoardGameInfo {
    private Cell[][] cells;
    private final List<Point> startingPoints;

    public BoardGameInfo(Cell[][] cells) {
        startingPoints = new ArrayList<>();
        initalizeStartingPoints();
        this.cells = cells;
        setBoard();

    }

    private void setBoard() {
        boolean even = true;
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                this.cells[i][j] = new Cell(CellType.CELL_EMPTY, new Point(i,j));
                if (even)
                    even = false;
                else
                    even = true;
            }
        }
        for (int i = 1; i < cells.length - 1; i = i + 2) {
            for (int j = 1; j < cells[i].length - 1; j = j + 2) {
                this.cells[i][j] = new Cell(CellType.CELL_WALL, new Point(i,j));
            }
        }
        randomizeCrateCells();
    }

    public void initalizeStartingPoints(){
        startingPoints.add(new Point(0,0));
        startingPoints.add(new Point(0,1));
        startingPoints.add(new Point(0,2));
        startingPoints.add(new Point(1,0));
        startingPoints.add(new Point(2,0));

        startingPoints.add(new Point(Constants.DEFAULT_GAME_TILES_HORIZONTALLY-1,0));
        startingPoints.add(new Point(Constants.DEFAULT_GAME_TILES_HORIZONTALLY-2,0));
        startingPoints.add(new Point(Constants.DEFAULT_GAME_TILES_HORIZONTALLY-3,0));
        startingPoints.add(new Point(Constants.DEFAULT_GAME_TILES_HORIZONTALLY-1,1));
        startingPoints.add(new Point(Constants.DEFAULT_GAME_TILES_HORIZONTALLY-1,2));

        startingPoints.add(new Point(0,Constants.DEFAULT_GAME_TILES_VERTICALLY-1));
        startingPoints.add(new Point(0,Constants.DEFAULT_GAME_TILES_VERTICALLY-2));
        startingPoints.add(new Point(0,Constants.DEFAULT_GAME_TILES_VERTICALLY-3));
        startingPoints.add(new Point(1,Constants.DEFAULT_GAME_TILES_VERTICALLY-1));
        startingPoints.add(new Point(2,Constants.DEFAULT_GAME_TILES_VERTICALLY-1));

        startingPoints.add(new Point(Constants.DEFAULT_GAME_TILES_HORIZONTALLY-1,Constants.DEFAULT_GAME_TILES_VERTICALLY-1));
        startingPoints.add(new Point(Constants.DEFAULT_GAME_TILES_HORIZONTALLY-1,Constants.DEFAULT_GAME_TILES_VERTICALLY-2));
        startingPoints.add(new Point(Constants.DEFAULT_GAME_TILES_HORIZONTALLY-1,Constants.DEFAULT_GAME_TILES_VERTICALLY-3));
        startingPoints.add(new Point(Constants.DEFAULT_GAME_TILES_HORIZONTALLY-2,Constants.DEFAULT_GAME_TILES_VERTICALLY-1));
        startingPoints.add(new Point(Constants.DEFAULT_GAME_TILES_HORIZONTALLY-3,Constants.DEFAULT_GAME_TILES_VERTICALLY-1));
    }

    public Cell[][] getCells() {
        return cells;
    }

    public List<Cell> getCrates() {
        List<Cell> crates = new ArrayList<>();
        for(Cell[] cell:cells){
            for(Cell cell1:cell){
                if(cell1.getType()==CellType.CELL_CRATE){
                    crates.add(cell1);
                }
            }
        }
        return crates;
    }

    private boolean checkIfIsNotStartingPoint(Point point){
        for (Point points:startingPoints) {
            if(point.equals(points)){
                return true;
            }
        }
        return false;
    }
    private void randomizeCrateCells() {
        Random random = new Random();
        for (int i = 0; i <cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (random.nextBoolean() && cells[i][j].getType() != CellType.CELL_WALL && !checkIfIsNotStartingPoint(new Point(i,j))) {
                    this.cells[i][j] = new Cell(CellType.CELL_CRATE, new Point(i,j));
                }
            }
        }
    }
}
