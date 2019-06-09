package pl.dszi.board;

import pl.dszi.engine.Time;
import pl.dszi.engine.constant.Constants;
import pl.dszi.player.ManualPlayerController;
import pl.dszi.player.Player;
import pl.dszi.player.noob.Node;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class BoardGame implements Cloneable {

    private Map<Player, Point> map = new HashMap<>();

    private BoardGameInfo boardGameInfo;

    public BoardGame(Cell[][] cells) {
        this.boardGameInfo = new BoardGameInfo(cells);
        BoardGameInitializator.initializeBoard(boardGameInfo);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    private Set<Player> getPlayers() {
        return new HashSet<>(map.keySet());
    }

    public List<Player> getAllNonManualPlayers() {
        List<Player> playerList = new ArrayList<>(map.keySet());
        return playerList.stream().filter(player -> !player.getIfManual()).collect(Collectors.toList());
    }

    public BoardGameInfo getInfo() {
        return boardGameInfo;
    }

    public Player getPlayerByName(String playerName) {
        Optional<Player> optionalPlayer = getPlayers().stream().filter(player -> player.getName().equals(playerName)).findAny();
        return optionalPlayer.orElseGet(() -> new Player("Sample Player", Constants.DEFAULT_PLAYER_HP, new ManualPlayerController()));
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
        for (Cell[] columns : boardGameInfo.getCells()) {
            for (Cell aCell : columns) {
                if (this.pointIsInsideBody(getPlayerPosition(player), aCell.getBody())) {
                    return aCell;
                }
            }
        }
        return null;
    }

    private Set<Cell> getAllExplosions() {

        Set<Cell> explosionCells = new HashSet<>();
        for (Cell[] columns :this.getInfo().getCells()) {
            for (Cell aCell : columns) {
                if (aCell.getType() == CellType.CELL_BOOM_CENTER) {
                    explosionCells.add(aCell);
                }
            }
        }
        return explosionCells;
    }


    public Rectangle getPlayerBody(Player player){
        Point location = getMap().get(player);
        return new Rectangle(location.x,location.y,Constants.DEFAULT_CELL_SIZE,Constants.DEFAULT_CELL_SIZE);
    }
    public void damageAllPlayersIntersectingWithExplosion(){
        for(Player player: getMap().keySet()) {
            if(getAllExplosions().stream().anyMatch(explo -> explo.getBody().intersects(getPlayerBody(player)))&& player.isMortal())
                player.damagePlayer();
        }
    }

    public void move(Player player, Direction direction) {
        if (!player.isAlive()) {
            return;
        }
        Point newPoint = new Point(map.get(player).x + direction.x, map.get(player).y + direction.y);
        if (targetSpaceIsInsideBoardGame(newPoint) && !checkIfFieldIsObstacle(player, newPoint)) {
            map.replace(player, newPoint);
        }
    }

    private boolean targetSpaceIsInsideBoardGame(Point point) {
        return pointIsInsideBody(point, new Rectangle(0,
                0,
                Constants.DEFAULT_CELL_SIZE * (Constants.DEFAULT_GAME_TILES_HORIZONTALLY - 1) + Constants.DEFAULT_SPEED,
                Constants.DEFAULT_CELL_SIZE * (Constants.DEFAULT_GAME_TILES_VERTICALLY - 1) + Constants.DEFAULT_SPEED
        ));
    }

    private boolean pointIsInsideBody(Point aPoint, Rectangle rectangle) {
        return rectangle.contains(aPoint);
    }

    private boolean playerInterfereWithCell(Rectangle rectangle1, Rectangle rectangle2) {
        return rectangle1.intersects(rectangle2);
    }


    private boolean checkIfFieldIsObstacle(Player player, Point point) {
        Rectangle pointToBody = new Rectangle(point.x, point.y, Constants.DEFAULT_CELL_SIZE, Constants.DEFAULT_CELL_SIZE);
        for (Cell[] cell : boardGameInfo.getCells()) {
            for (Cell aCell : cell) {
                if (playerInterfereWithCell(pointToBody, aCell.getBody()) && (!aCell.getType().walkable)) {
                    return true;
                }
            }
        }
        if (checkIfBombForward(player, pointToBody)) {
            return true;
        } else
            return checkIfCrateForward(pointToBody);
    }

    private boolean checkIfCrateForward(Rectangle body) {
        for (Cell[] columns : getInfo().getCells()) {
            for (Cell crateCell : columns) {
                if (crateCell.getType() == CellType.CELL_CRATE && crateCell.getBody().intersects(body)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkIfCrateAtSpecificIndex(Point aCoord) {
        return boardGameInfo.getCells()[aCoord.x][aCoord.y].getType() == CellType.CELL_CRATE;
    }

    public boolean checkIfBombForward(Player player, Rectangle body) {
        List<Cell> intersectsBombs = getNeighbors(getPlayerPositionCellByCenter(player));
        intersectsBombs.add(getPlayerPositionCellByCenter(player));
        intersectsBombs = intersectsBombs.stream().filter(cell -> cell.getType() == CellType.CELL_BOMB && body.intersects(cell.getBody())).collect(Collectors.toList());
        if (intersectsBombs.size() == 0 && player.isInsideBomb()) {
            player.setInsideBomb(false);
        return false;
    }
        return !player.isInsideBomb() && intersectsBombs.size() > 0;
    }

    public boolean plantBomb(Player player) {
        try {
            if (!player.isAlive()) {
                System.out.println(player.getName() + " is dead");
                return false;
            }
            Cell playerPosition = getPlayerPositionCellByCenter(player);
            assert playerPosition != null;
            if (player.canPlantBomb()&&playerPosition.getType() == CellType.CELL_EMPTY) {
                playerPosition.setType(CellType.CELL_BOMB);
                setInterectionWithBomb(playerPosition);
                detonateTimer(playerPosition);
               // System.out.println("Bomb Planted");
                player.plantBomb();
                player.restoreBombAmountEvent();
                return true;
            }
        } catch (NullPointerException exception) {
            System.err.println("Cant plant bomb for some reason");
        }
        return false;
    }

    private Rectangle getBodyFromPoint(Point point) {
        return new Rectangle(point.x, point.y, Constants.DEFAULT_CELL_SIZE, Constants.DEFAULT_CELL_SIZE);
    }

    private void setInterectionWithBomb(Cell cell) {
        for (Player player : getMap().keySet()) {
            if (getBodyFromPoint(getPlayerPosition(player)).intersects(cell.getBody())) {
                player.setInsideBomb(true);
            }
        }
    }

    public Cell getPlayerPositionCellByCenter(Player player) {
        Rectangle playerBody = new Rectangle(getPlayerPosition(player).x, getPlayerPosition(player).y, Constants.DEFAULT_CELL_SIZE, Constants.DEFAULT_CELL_SIZE);
        for (Cell[] columns : boardGameInfo.getCells()) {
            for (Cell aCell : columns) {
                if (this.pointIsInsideBody(new Point((int) Math.round(playerBody.getCenterX()), (int) Math.round(playerBody.getCenterY())), aCell.getBody())) {
                    return aCell;
                }
            }
        }
        return null;
    }

    private Point getCellCordByBomb(Cell bombCell) {
        for (int i = 0; i < getInfo().getCells().length; i++) {
            for (int j = 0; j < boardGameInfo.getCells()[i].length; j++) {
                if (boardGameInfo.getCells()[i][j].getPoint().equals(bombCell.point))
                    return new Point(i, j);
            }
        }
        return new Point(0, 0);
    }


    private void detonateBomb(Cell bombCell) {
        Set<Cell> cellsToExplode = getCellsToExplosion(getCellCordByBomb(bombCell));
        cellsToExplode.forEach(cell -> {
                   cell.setType(CellType.CELL_BOOM_CENTER);
                    this.setEstinguishTimer(cell);
                }
        );
    }

    private void setEstinguishTimer(Cell cell) {
        Time.scheduleTimer(() -> cell.setType(CellType.CELL_EMPTY), Constants.BASIC_BOMB_EXPLOSION_BURNING_TIMER*100);
    }



    private void detonateTimer(Cell cell) {
        Time.scheduleTimer(() -> detonateBomb(cell), Constants.BASIC_BOMB_EXPLOSION_TIMER*100);
    }

    public Cell[][] getCells() {
        return boardGameInfo.getCells();
    }

    private boolean checkIfFieldIsEmpty(Point point) {
        return !map.containsValue(point);
    }

    private Set<Cell> getCellsToExplosion(Point point) {
        return new HashSet<>(getAccessibleNeighbors(getInfo().getCells()[point.x][point.y],Constants.DEFAULT_RANGE));
    }

    public boolean checkIfNeighborIsCrate(Cell aCell) {
        int row = aCell.getPoint().x;
        int col = aCell.getPoint().y;
        if (row - 1 >= 0 && boardGameInfo.getCells()[row - 1][col].getType() == CellType.CELL_CRATE) {
            return true;
        }
        if (col - 1 >= 0 && boardGameInfo.getCells()[row][col - 1].getType() == CellType.CELL_CRATE) {
            return true;
        }
        if (row + 1 < Constants.DEFAULT_GAME_TILES_HORIZONTALLY && boardGameInfo.getCells()[row + 1][col].getType() == CellType.CELL_CRATE) {
            return true;
        }
        return col + 1 < Constants.DEFAULT_GAME_TILES_VERTICALLY && boardGameInfo.getCells()[row][col + 1].getType() == CellType.CELL_CRATE;
    }

    private List<Cell> getNeighbors(Cell aCell) {
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

    public Set<Cell> getAccessibleNeighbors(Cell aCell,int rangeChecker) {
        Set<Cell> neighbors = new HashSet<>();
        boolean north = true;
        boolean west = true;
        boolean east = true;
        boolean south = true;
        for (int range = 1; range < Constants.DEFAULT_RANGE; range++) {
            int row = aCell.getPoint().x;
            int col = aCell.getPoint().y;
            if (west &&
                    row - range >= 0 &&
                    boardGameInfo.getCells()[row - range][col].getType() != CellType.CELL_WALL &&
                    boardGameInfo.getCells()[row - range][col].getType() != CellType.CELL_BOMB) {
                neighbors.add(boardGameInfo.getCells()[row - range][col]);
                if (checkIfCrateAtSpecificIndex(new Point(row - range, col))) {
                    west = false;
                }
            } else
                west = false;
            if (north &&
                    col - range >= 0 &&
                    boardGameInfo.getCells()[row][col - range].getType() != CellType.CELL_WALL &&
                    boardGameInfo.getCells()[row][col - range].getType() != CellType.CELL_BOMB) {
                neighbors.add(boardGameInfo.getCells()[row][col - range]);
                if (checkIfCrateAtSpecificIndex(new Point(row, col - range))) {
                    north = false;
                }
            } else
                north = false;
            if (east &&
                    row + range < Constants.DEFAULT_GAME_TILES_HORIZONTALLY &&
                    boardGameInfo.getCells()[row + range][col].getType() != CellType.CELL_WALL &&
                    boardGameInfo.getCells()[row + range][col].getType() != CellType.CELL_BOMB) {
                neighbors.add(boardGameInfo.getCells()[row + range][col]);
                if (checkIfCrateAtSpecificIndex(new Point(row + range, col))) {
                    east = false;
                }
            } else
                east = false;
            if (south &&
                    col + range < Constants.DEFAULT_GAME_TILES_VERTICALLY &&
                    boardGameInfo.getCells()[row][col + range].getType() != CellType.CELL_WALL &&
                    boardGameInfo.getCells()[row][col + range].getType() != CellType.CELL_BOMB) {
                neighbors.add(boardGameInfo.getCells()[row][col + range]);
                if (checkIfCrateAtSpecificIndex(new Point(row, col + range))) {
                    south = false;
                }
            } else
                south = false;
        }
         neighbors.add(aCell);
        return neighbors;
    }
}
