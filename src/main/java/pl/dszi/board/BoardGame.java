package pl.dszi.board;

import pl.dszi.engine.Constants;
import pl.dszi.player.ManualPlayerController;
import pl.dszi.player.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BoardGame implements Cloneable {

    private Map<Player, Point> map = new HashMap<>();
    private BoardGameInfo boardGameInfo;
    private Map<BombCell, Rectangle> bombs = new ConcurrentHashMap<>();

    public BoardGame(Cell[][] cells) {
        this.boardGameInfo = new BoardGameInfo(cells);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    private List<Player> getPlayers() {
        List<Player> players = new ArrayList<>();
        map.forEach(((player, point) -> players.add(player)));
        return players;
    }

    public List<Player> getAllNonManualPlayers() {
        List<Player> players = new ArrayList<>();
        for (Map.Entry<Player, Point> playerPointEntry : map.entrySet()) {
            if (!playerPointEntry.getKey().getIfManual()) {
                players.add(playerPointEntry.getKey());
            }
        }
        return players;
    }

    public BoardGameInfo getInfo() {
        return boardGameInfo;
    }

    public Player getPlayerByName(String playerName) {
        for (Player player : getPlayers()) {
            if (player.getName().equals(playerName)) {
                return player;
            }
        }
        return new Player("Sample Player", Constants.DEFAULT_PLAYER_HP, new ManualPlayerController());
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

    public Cell getPlayerPositionCell(Player player) {
        Rectangle pointToBody = new Rectangle(getPlayerPosition(player).x, getPlayerPosition(player).y, Constants.DEFAULT_CELL_SIZE, Constants.DEFAULT_CELL_SIZE);
        for (Cell[] cell : boardGameInfo.getCells()) {
            for (Cell aCell : cell) {
                if (this.pointIsInsideBody(new Point(pointToBody.x, pointToBody.y), aCell.getBody())) {
                    return aCell;
                }
            }
        }
        return null;
    }

    public boolean move(Player player, Direction direction) {
            Point newPoint = new Point(map.get(player).x + direction.x, map.get(player).y + direction.y);
            if (targetSpaceIsInsideBoardGame(newPoint) && !checkIfFieldIsObstacle(player, newPoint)) {
                map.replace(player, newPoint);
                return true;
            } else {
                return false;
            }
    }

    private boolean targetSpaceIsInsideBoardGame(Point point) {
        return pointIsInsideBody(point, new Rectangle(Constants.DEFAULT_CELL_SIZE,
                Constants.DEFAULT_CELL_SIZE,
                Constants.DEFAULT_CELL_SIZE * (Constants.DEFAULT_GAME_TILES_HORIZONTALLY),
                Constants.DEFAULT_CELL_SIZE * (Constants.DEFAULT_GAME_TILES_VERTICALLY)
        ));
    }


    private boolean pointIsInsideBody(Point aPoint, Rectangle rectangle) {
        return rectangle.contains(aPoint);
    }

    private boolean playerInterfereWithWall(Rectangle rectangle1, Rectangle rectangle2) {
        return rectangle1.intersects(rectangle2);
    }


    public Map<BombCell, Rectangle> getBombs() {
        return bombs;
    }

    public boolean checkIfFieldIsObstacle(Player player, Point point) {
        Rectangle pointToBody = new Rectangle(point.x, point.y, Constants.DEFAULT_CELL_SIZE, Constants.DEFAULT_CELL_SIZE);
        for (Cell[] cell : boardGameInfo.getCells()) {
            for (Cell aCell : cell) {
                if (playerInterfereWithWall(pointToBody, aCell.getBody()) && !aCell.getType().walkable) {
                    System.out.println(aCell);
                    return true;
                }
            }
        }
        if (checkIfBombForward(player, pointToBody)) {
            return true;
        }
        if(checkIfCrateForward(pointToBody))
        {
            return true;
        }
        return false;
    }

    public boolean checkIfCrateForward(Rectangle body){
       for(CrateCell[] crateCells:getInfo().getCrates()){
           for(CrateCell crateCell:crateCells){
               if(crateCell!=null && crateCell.getBody().intersects(body)){
                   return true;
               }
           }
       }
        return false;
    }
    public boolean checkIfBombForward(Player player, Rectangle body) {
        for (BombCell bomb : bombs.keySet()) {
            if (!bomb.body.intersects(body) && bomb.getPlayer() == player) {
                bomb.setPlayerInside(false);
            }
            if (bomb.body.intersects(body) && bomb.getPlayer() != player || (bomb.body.intersects(body) && bomb.getPlayer() == player && !bomb.isPlayerInside())) {
                return true;
            }
        }
        return false;
    }

    public synchronized void plantBomb(Player player) {
        try {
            Cell playerPositionCell = getPlayerPositionCell(player);
            for (int i = 0; i < boardGameInfo.getCells().length; i++) {
                for (int j = 0; j < boardGameInfo.getCells()[i].length; j++) {
                    if (boardGameInfo.getCells()[i][j].equals(playerPositionCell) && boardGameInfo.getCells()[i][j].getType() != CellType.CELL_BOMB) {
                        System.out.println("Bomb Planted");
                        BombCell bombCell = new BombCell(boardGameInfo.getCells()[i][j], player);
                        bombs.put(bombCell, bombCell.getBody());
                        player.plantBomb();
                    }
                }
            }
        } catch (NullPointerException exception) {
            System.err.println("Cant plant bomb for some reason");
        }
    }

    public Cell[][] getCells(){
        return boardGameInfo.getCells();
    }
    private boolean checkIfFieldIsEmpty(Point point) {
        return !map.containsValue(point);
    }

    public List<Cell> getNeighbors(Cell aCell) {
        List<Cell> neighbors = new ArrayList<>();
        int row = 0;
        int col = 0;
        for (int i = 0; i < boardGameInfo.getCells().length; i++) {
            for (int j = 0; j < boardGameInfo.getCells()[i].length; j++) {
                if (boardGameInfo.getCells()[i][j].equals(aCell)) {
                    row = i;
                    col = j;
                    if (row - 1 >= 0) {
                        neighbors.add(boardGameInfo.getCells()[row - 1][col]);
                    }
                    if (col - 1 >= 0) {
                        neighbors.add(boardGameInfo.getCells()[row][col - 1]);
                    }
                    if (row + 1 < Constants.DEFAULT_GAME_TILES_HORIZONTALLY) {
                        neighbors.add(boardGameInfo.getCells()[row + 1][col]);
                    }
                    if (col + 1 < Constants.DEFAULT_GAME_TILES_VERTICALLY) {
                        neighbors.add(boardGameInfo.getCells()[row][col + 1]);
                    }
                }
            }
        }
        return neighbors;
    }
}
