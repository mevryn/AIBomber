package pl.dszi.board;

import pl.dszi.Booster.Booster;
import pl.dszi.GeneticAlgorithm.GA;
import pl.dszi.GeneticAlgorithm.Population;
import pl.dszi.engine.Game;
import pl.dszi.engine.GameStatus;
import pl.dszi.engine.Time;
import pl.dszi.engine.constant.Constant;
import pl.dszi.player.Player;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class BoardGame {

    private Map<Player, Point> map = new HashMap<>();

    private BoardGameInfo boardGameInfo;

    public BoardGame(Cell[][] cells) {
        this.boardGameInfo = new BoardGameInfo(cells);
        BoardGameInitializator.initializeBoard(boardGameInfo);
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
        return optionalPlayer.orElseGet(() -> new Player("Sample Player", Constant.DEFAULT_PLAYER_HP));
    }

    public boolean put(Player player, Point point) {
        if (checkIfFieldIsEmpty(point)) {
            map.put(player, point);
            return true;
        } else {
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

        Cell playerCell = Arrays.stream(getCells()).
                 forEach(cells -> Arrays.stream(cells).
                 filter(cell -> this.pointIsInsideBody(getPlayerPosition(player),cell.getBody())).findAny().get());
        for (Cell[] columns : boardGameInfo.getCells()) {
            for (Cell aCell : columns) {
                if (this.pointIsInsideBody(getPlayerPosition(player), aCell.getBody())) {
                    return aCell;
                }
            }
        }
        return null;
    }


    public Rectangle getPlayerBody(Player player) {
        Point location = getMap().get(player);
        return new Rectangle(location.x, location.y, Constant.DEFAULT_CELL_SIZE, Constant.DEFAULT_CELL_SIZE);
    }

    public void damageAllPlayersIntersectingWithExplosion() {
        for (Player player : getMap().keySet()) {
            if (getInfo().getAllSpecificCells(CellType.CELL_BOOM_CENTER).stream().anyMatch(explosion -> explosion.getBody().intersects(getPlayerBody(player))) && player.isMortal())
                player.damagePlayer(Game.gameStatus);
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
                Constant.DEFAULT_CELL_SIZE * (Constant.DEFAULT_GAME_TILES_HORIZONTALLY - 1) + Constant.DEFAULT_SPEED,
                Constant.DEFAULT_CELL_SIZE * (Constant.DEFAULT_GAME_TILES_VERTICALLY - 1) + Constant.DEFAULT_SPEED
        ));
    }

    private boolean pointIsInsideBody(Point aPoint, Rectangle rectangle) {
        return rectangle.contains(aPoint);
    }

    private boolean playerInterfereWithCell(Rectangle rectangle1, Rectangle rectangle2) {
        return rectangle1.intersects(rectangle2);
    }

    private Population boosters = null;
    private boolean firstRun = true;

    private boolean checkIfFieldIsObstacle(Player player, Point point) {
        Rectangle pointToBody = new Rectangle(point.x, point.y, Constant.DEFAULT_CELL_SIZE, Constant.DEFAULT_CELL_SIZE);
        for (Cell[] cell : boardGameInfo.getCells()) {
            for (Cell aCell : cell) {
                if (playerInterfereWithCell(pointToBody, aCell.getBody()) && (!aCell.getType().walkable)) {
                    return true;
                } else if (playerInterfereWithCell(pointToBody, aCell.getBody()) && aCell.getType() == CellType.CELL_BOOSTER) {
                    if (Game.gameStatus == GameStatus.RUNNING && firstRun) {
                        firstRun = false;
                        boosters = null;
                    }

                    if (boosters == null) {
                        boosters = new Population(10, true);
                    } else {
                        boosters = GA.evolvePopulation(boosters);
                    }
                    Booster booster = boosters.getFittest();
                    if (!firstRun) {
                        booster.setPlayer(player);
                    }
                    aCell.setType(CellType.CELL_EMPTY);
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
                if ((crateCell.getType() == CellType.CELL_CRATE || crateCell.getType() == CellType.CELL_CRATEBONUS) && crateCell.getBody().intersects(body)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkIfCrateAtSpecificIndex(Point aCoord) {
        return boardGameInfo.getCells()[aCoord.x][aCoord.y].getType() == CellType.CELL_CRATE || boardGameInfo.getCells()[aCoord.x][aCoord.y].getType() == CellType.CELL_CRATEBONUS;
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
            if (player.canPlantBomb() && playerPosition.getType() == CellType.CELL_EMPTY) {
                playerPosition.setType(CellType.CELL_BOMB);
                setInterectionWithBomb(playerPosition);
                detonateTimer(playerPosition, player.getBombRange());
                player.plantBomb();
                player.restoreBombAmountEvent();
                return true;
            }
        } catch (NullPointerException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    private Rectangle getBodyFromPoint(Point point) {
        return new Rectangle(point.x, point.y, Constant.DEFAULT_CELL_SIZE, Constant.DEFAULT_CELL_SIZE);
    }

    private void setInterectionWithBomb(Cell cell) {
        for (Player player : getMap().keySet()) {
            if (getBodyFromPoint(getPlayerPosition(player)).intersects(cell.getBody())) {
                player.setInsideBomb(true);
            }
        }
    }

    public Cell getPlayerPositionCellByCenter(Player player) {
        Rectangle playerBody = new Rectangle(getPlayerPosition(player).x, getPlayerPosition(player).y, Constant.DEFAULT_CELL_SIZE, Constant.DEFAULT_CELL_SIZE);
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


    private void detonateBomb(Cell bombCell, int bombRange) {
        Set<Cell> cellsToExplode = getCellsToExplosion(getCellCordByBomb(bombCell), bombRange);
        cellsToExplode.forEach(cell -> {
                    if (cell.getType() == CellType.CELL_CRATEBONUS) {
                        cell.setType(CellType.CELL_BOOSTER);
                    } else {
                        cell.setType(CellType.CELL_BOOM_CENTER);
                        this.setEstinguishTimer(cell);
                    }
                }
        );
    }

    private void setEstinguishTimer(Cell cell) {
        Time.scheduleTimer(() -> cell.setType(CellType.CELL_EMPTY), Constant.BASIC_BOMB_EXPLOSION_BURNING_TIMER);
    }

    private void detonateTimer(Cell cell, int bombRange) {
        Time.scheduleTimer(() -> detonateBomb(cell, bombRange), Constant.BASIC_BOMB_EXPLOSION_TIMER);
    }

    public Cell[][] getCells() {
        return boardGameInfo.getCells();
    }

    private boolean checkIfFieldIsEmpty(Point point) {
        return !map.containsValue(point);
    }

    private Set<Cell> getCellsToExplosion(Point point, int bombRange) {
        return new HashSet<>(getAccessibleNeighbors(getInfo().getCells()[point.x][point.y], bombRange));
    }

    public boolean checkIfNeighborIsCrate(Cell aCell) {
        int row = aCell.getPoint().x;
        int col = aCell.getPoint().y;
        if (row - 1 >= 0 && (boardGameInfo.getCells()[row - 1][col].getType() == CellType.CELL_CRATE || boardGameInfo.getCells()[row - 1][col].getType() == CellType.CELL_CRATEBONUS)) {
            return true;
        }
        if (col - 1 >= 0 && (boardGameInfo.getCells()[row][col - 1].getType() == CellType.CELL_CRATE) || boardGameInfo.getCells()[row][col - 1].getType() == CellType.CELL_CRATEBONUS) {
            return true;
        }
        if (row + 1 < Constant.DEFAULT_GAME_TILES_HORIZONTALLY && (boardGameInfo.getCells()[row + 1][col].getType() == CellType.CELL_CRATE || boardGameInfo.getCells()[row + 1][col].getType() == CellType.CELL_CRATEBONUS)) {
            return true;
        }
        return col + 1 < Constant.DEFAULT_GAME_TILES_VERTICALLY && (boardGameInfo.getCells()[row][col + 1].getType() == CellType.CELL_CRATE || boardGameInfo.getCells()[row][col + 1].getType() == CellType.CELL_CRATEBONUS);
    }

    private List<Cell> getNeighbors(Cell aCell) {
        List<Cell> neighbors = new ArrayList<>();
        int row;
        int col;
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
                    if (row + 1 < Constant.DEFAULT_GAME_TILES_HORIZONTALLY) {
                        neighbors.add(boardGameInfo.getCells()[row + 1][col]);
                    }
                    if (col + 1 < Constant.DEFAULT_GAME_TILES_VERTICALLY) {
                        neighbors.add(boardGameInfo.getCells()[row][col + 1]);
                    }
                }
            }
        }
        return neighbors;
    }

    public Set<Cell> getAccessibleNeighbors(Cell aCell, int rangeChecker) {
        Set<Cell> neighbors = new HashSet<>();
        boolean north = true;
        boolean west = true;
        boolean east = true;
        boolean south = true;
        for (int range = 1; range < rangeChecker; range++) {
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
                    row + range < Constant.DEFAULT_GAME_TILES_HORIZONTALLY &&
                    boardGameInfo.getCells()[row + range][col].getType() != CellType.CELL_WALL &&
                    boardGameInfo.getCells()[row + range][col].getType() != CellType.CELL_BOMB) {
                neighbors.add(boardGameInfo.getCells()[row + range][col]);
                if (checkIfCrateAtSpecificIndex(new Point(row + range, col))) {
                    east = false;
                }
            } else
                east = false;
            if (south &&
                    col + range < Constant.DEFAULT_GAME_TILES_VERTICALLY &&
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
