package pl.dszi.board;

import pl.dszi.engine.constant.Constants;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class BoardGameInfo {
    private Cell[][] cells;
    private final List<Point> startingPoints;

    BoardGameInfo(Cell[][] cells) {
        startingPoints = new ArrayList<>();
        initalizeStartingPoints();
        this.cells = cells;
    }



    private void initalizeStartingPoints(){
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

    boolean checkIfIsNotStartingPoint(Point point){
        return startingPoints.stream().noneMatch(point1 -> point1.equals(point));
    }
    public Set<Cell> getAllBombs (){
        Set<Cell> bombs = new HashSet<>();
        for(Cell[] columns:cells){
            for(Cell cell:columns){
                if(cell.getType()==CellType.CELL_BOMB)
                    bombs.add(cell);
            }
        }
        return bombs;
    }

    public void setAllCellsToEmpty(){
        for(Cell[] columns:cells){
            for(Cell cell:columns){
                if(cell.getType()==CellType.CELL_CRATE)
                    cell.setType(CellType.CELL_EMPTY);
            }
        }
    }
    public Set<Cell> getAllCrates (){
        Set<Cell> crates = new HashSet<>();
        for(Cell[] columns:cells){
            for(Cell cell:columns){
                if(cell.getType()==CellType.CELL_CRATE || cell.getType() == CellType.CELL_CRATEBONUS)
                crates.add(cell);
            }
        }
        return crates;
    }

}
