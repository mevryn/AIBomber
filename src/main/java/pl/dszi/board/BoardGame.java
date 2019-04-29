package pl.dszi.board;

import pl.dszi.engine.Constants;
import pl.dszi.player.ManualPlayerController;
import pl.dszi.player.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardGame implements Cloneable {
    final Cell[][] cells;
    private final Map<Cell, Point> wallCellMap;
    private Map<Player, Point> map = new HashMap<>();
    public BoardGame(Cell[][] cells) {
        this.cells = cells;
        wallCellMap = new HashMap<>();
        setBoard();
    }

    public Cell[][] getCells() {
        return cells;
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

    private void setBoard() {
        boolean even = true;
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                this.cells[i][j] = new Cell(CellType.CELL_EMPTY, new Point((i + 1) * Constants.DEFAULT_CELL_SIZE, (j + 1) * Constants.DEFAULT_CELL_SIZE), even);
                if (even)
                    even = false;
                else
                    even = true;
            }
        }
        for (int i = 1; i < cells.length - 1; i = i + 2) {
            for (int j = 1; j < cells[i].length - 1; j = j + 2) {
                this.cells[i][j] = new Cell(CellType.CELL_WALL, new Point((i + 1) * Constants.DEFAULT_CELL_SIZE, (j + 1) * Constants.DEFAULT_CELL_SIZE));
                wallCellMap.put(this.cells[i][j], this.cells[i][j].getPoint());
            }
        }
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

    private Cell getPlayerPositionCell(Player player) {
        Rectangle pointToBody = new Rectangle(getPlayerPosition(player).x, getPlayerPosition(player).y, Constants.DEFAULT_CELL_SIZE, Constants.DEFAULT_CELL_SIZE);

        for (Cell[] cell : cells) {
            for (Cell aCell : cell) {
                if (this.pointIsInsideBody(new Point((int)pointToBody.getCenterX(),(int)pointToBody.getCenterY()), aCell.getBody())) {
                    return aCell;
                }
            }
        }
        return null;
    }

    public void move(Player player, Direction direction) {
        try {
            Point newPoint = new Point(map.get(player).x + direction.x, map.get(player).y + direction.y);
            if (!map.containsValue(newPoint) && targetSpaceIsInsideBoardGame(newPoint) && !checkIfFieldIsObstacle(newPoint)) {
                map.replace(player, newPoint);
                //return getPlayerPosition(player);
            } else {
                System.err.println("Can't move here. Field is not empty");
                // return getPlayerPosition(player);
            }
        } catch (NullPointerException nullPointer) {
            nullPointer.printStackTrace();
        }
    }

    private boolean targetSpaceIsInsideBoardGame(Point point) {
        return pointIsInsideBody(point, new Rectangle(Constants.DEFAULT_CELL_SIZE, Constants.DEFAULT_CELL_SIZE, Constants.DEFAULT_CELL_SIZE * Constants.DEFAULT_GAME_TILES_HORIZONTALLY, Constants.DEFAULT_CELL_SIZE * Constants.DEFAULT_GAME_TILES_VERTICALLY));

    }


    private boolean pointIsInsideBody(Point aPoint, Rectangle rectangle) {
        return rectangle.contains(aPoint);
    }

    private boolean playerInterfereWithWall(Rectangle rectangle1, Rectangle rectangle2) {
        return rectangle1.intersects(rectangle2);
    }

    private boolean checkIfFieldIsObstacle(Point point) {
        Rectangle pointToBody = new Rectangle(point.x, point.y, Constants.DEFAULT_CELL_SIZE, Constants.DEFAULT_CELL_SIZE);

        for (Cell[] cell : cells) {
            for (Cell aCell : cell) {
                if (playerInterfereWithWall(pointToBody, aCell.getBody()) && aCell.getType() == CellType.CELL_WALL) {
                    System.out.println(aCell.getBody());
                    return true;
                }
            }
        }
        return false;
    }

    public void plantBomb(Player player) {
        System.out.println("Bomb Planted");
        try {
            Cell playerPositionCell = getPlayerPositionCell(player);
            for (Cell[] cell : cells) {
                for (Cell aCell : cell) {
                    if (aCell.equals(playerPositionCell)) {
                        aCell.setType(CellType.CELL_BOMB);
                    }
                }
            }
        } catch (NullPointerException exception) {
            System.err.println("Cant plant bomb for some reason");
        }

    }

    private boolean checkIfFieldIsEmpty(Point point) {
        return !map.containsValue(point);
    }
}
