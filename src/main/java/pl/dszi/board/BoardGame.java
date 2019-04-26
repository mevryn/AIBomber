package pl.dszi.board;

import pl.dszi.engine.Constants;
import pl.dszi.player.ManualPlayerController;
import pl.dszi.player.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class BoardGame implements Cloneable{
    private Map<Player, Point> map = new HashMap<>();
    public final int width;
    public final int height;

    public Cell[][] getCells() {
        return cells;
    }

    public final Cell[][] cells;

    public BoardGame(int width, int height, Cell[][] cells) {
        this.width = width;
        this.height = height;
        this.cells = cells;
        setBoard();
    }


    public List<Player> getPlayers(){
        List<Player> players = new ArrayList<>();
        map.forEach(((player, point) -> players.add(player)));
        return players;
    }

    private void setBoard() {
        boolean even = true;
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                this.cells[i][j] = new Cell(CellType.CELL_EMPTY, new Point(i * Constants.DEFAULT_CELL_SIZE, j * Constants.DEFAULT_CELL_SIZE), even);
                if (even)
                    even = false;
                else
                    even = true;
            }
        }
        for (int i = 1; i < cells.length; i=i+2) {
            for (int j = 1; j < cells[i].length; j=j+2) {
                this.cells[i][j] = new Cell(CellType.CELL_WALL, new Point(i * Constants.DEFAULT_CELL_SIZE, j * Constants.DEFAULT_CELL_SIZE));
            }
        }
    }

    public Player getPlayerByName(String playerName){
      for (Player player:getPlayers()){
          if(player.getName()==playerName){
              return player;
          }else{
              return null;
          }
      }
      return new Player("Sample Player",Constants.DEFAULT_PLAYER_HP,new ManualPlayerController());
    }
    public boolean put(Player player, Point point) {
        if (checkIfFieldIsEmpty(point)) {
            map.put(player, point);
            return true;
        } else {
            System.err.println("Point is not empty");
            return false;
        }

    }

    public Map<Player, Point> getMap() {
        return map;
    }

    public Point getPlayerPosition(Player player) {
        return map.get(player);
    }

    public Point move(Player player, Direction direction) {
        Point newPoint = new Point(getPlayerPosition(player).x + direction.x, getPlayerPosition(player).y + direction.y);
        if (!map.containsValue(newPoint) && targetSpaceIsInside(newPoint)&&checkIfFieldIsObstacle(newPoint)) {
            map.replace(player, newPoint);
            return getPlayerPosition(player);
        } else {
            System.err.println("Can't move here. Field is not empty");
            return getPlayerPosition(player);
        }
    }

    public boolean targetSpaceIsInside(Point point) {
        if (point.x>=Constants.DEFAULT_OFFSET &&
                point.y>=Constants.DEFAULT_OFFSET &&
                point.x < Constants.DEFAULT_GAME_WIDTH-Constants.DEFAULT_CELL_SIZE*Constants.DEFAULT_BORDER
                && point.y < Constants.DEFAULT_GAME_HEIGHT-Constants.DEFAULT_CELL_SIZE*Constants.DEFAULT_BORDER) {
                return true;
        } else
            return false;
    }

    boolean checkIfFieldIsObstacle(Point point){
       for(Cell cell[]:cells){
           for(Cell cell1:cell){
               if(cell1.getType() == CellType.CELL_WALL && cell1.getPoint()==point){
                   return false;
               }else
                   return true;
           }
       }
       return true;
    }
    boolean checkIfFieldIsEmpty(Point point) {
        return !map.containsValue(point);
    }
}
