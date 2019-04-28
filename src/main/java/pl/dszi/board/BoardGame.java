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

    public Cell[][] getCells() {
        return cells;
    }
    private final Map<Cell,Point> wallCellMap;
    final Cell[][] cells;

    public BoardGame(Cell[][] cells) {
        this.cells = cells;
        wallCellMap = new HashMap<>();
        setBoard();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    private List<Player> getPlayers(){
        List<Player> players = new ArrayList<>();
        map.forEach(((player, point) -> players.add(player)));
        return players;
    }
    public List<Player> getAllNonManualPlayers(){
        List<Player> players = new ArrayList<>();
        for (Map.Entry<Player, Point> playerPointEntry : map.entrySet()) {
            if(!playerPointEntry.getKey().getIfManual()){
                players.add(playerPointEntry.getKey());
            }
        }
        return players;
    }
    private void setBoard() {
        boolean even = true;
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                this.cells[i][j] = new Cell(CellType.CELL_EMPTY, new Point((i+1) * Constants.DEFAULT_CELL_SIZE, (j+1) * Constants.DEFAULT_CELL_SIZE), even);
                if (even)
                    even = false;
                else
                    even = true;
            }
        }
        for (int i = 1; i < cells.length; i=i+2) {
            for (int j = 1; j < cells[i].length; j=j+2) {
                this.cells[i][j] = new Cell(CellType.CELL_WALL, new Point((i+1) * Constants.DEFAULT_CELL_SIZE, (j+1) * Constants.DEFAULT_CELL_SIZE));
                wallCellMap.put(this.cells[i][j],this.cells[i][j].getPoint());
            }
        }
    }

    public Player getPlayerByName(String playerName){
      for (Player player:getPlayers()){
          if(player.getName().equals(playerName)){
              return player;
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

    public void move(Player player, Direction direction) {
        try {
            Point newPoint = new Point(map.get(player).x + direction.x, map.get(player).y + direction.y);
            if (!map.containsValue(newPoint) && targetSpaceIsInsideBoardGame(newPoint)) {
                map.replace(player, newPoint);
                //return getPlayerPosition(player);
            } else {
                System.err.println("Can't move here. Field is not empty");
                // return getPlayerPosition(player);
            }
        }catch (NullPointerException nullPointer){
            nullPointer.printStackTrace();
        }
    }

    private boolean targetSpaceIsInsideBoardGame(Point point) {
        return pointIsInsideBody(point,new Rectangle(Constants.DEFAULT_CELL_SIZE,Constants.DEFAULT_CELL_SIZE,Constants.DEFAULT_CELL_SIZE*Constants.DEFAULT_GAME_TILES_HORIZONTALLY-Constants.DEFAULT_CELL_SIZE,Constants.DEFAULT_CELL_SIZE*Constants.DEFAULT_GAME_TILES_VERTICALLY-Constants.DEFAULT_CELL_SIZE));
    }


    private boolean pointIsInsideBody(Point aPoint,Rectangle rectangle){
        return aPoint.x >= rectangle.x &&
                aPoint.y >= rectangle.y &&
                aPoint.x <= rectangle.x+rectangle.width &&
                        aPoint.y <= rectangle.x+rectangle.height;
    }
    private boolean checkIfFieldIsObstacle(Point point){
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if(cells[i][j].getType()==CellType.CELL_WALL && pointIsInsideBody(point,new Rectangle(cells[i][j].getPoint().x,cells[i][j].getPoint().y,Constants.DEFAULT_CELL_SIZE,Constants.DEFAULT_CELL_SIZE))){
                    System.out.println(cells[i][j].getBody());
                    return true;
                }
            }
        }
        return false;
    }
    private boolean checkIfFieldIsEmpty(Point point) {
        return !map.containsValue(point);
    }
}
