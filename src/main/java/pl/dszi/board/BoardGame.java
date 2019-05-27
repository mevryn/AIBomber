package pl.dszi.board;

import pl.dszi.engine.constant.Constants;
import pl.dszi.player.ManualPlayerController;
import pl.dszi.player.Player;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class BoardGame implements Cloneable {

    private Map<Player, Point> map = new HashMap<>();

    private BoardGameInfo boardGameInfo;

    public BoardGame(Cell[][] cells) {
        this.boardGameInfo = new BoardGameInfo(cells);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    private List<Player> getPlayers() {
        return new ArrayList<>(map.keySet());
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
        for (Cell[] cell : boardGameInfo.getCells()) {
            for (Cell aCell : cell) {
                if (this.pointIsInsideBody(getPlayerPosition(player), aCell.getBody())) {
                    return aCell;
                }
            }
        }
        return null;
    }

    public void move(Player player, Direction direction) {
        Point newPoint = new Point(map.get(player).x + direction.x, map.get(player).y + direction.y);
        if(!player.isAlive()){
            System.out.println(player.getName()+" is dead");
            return;
        }
        if (targetSpaceIsInsideBoardGame(newPoint) && !checkIfFieldIsObstacle(player, newPoint)) {
            map.replace(player, newPoint);
        }
    }

    private boolean targetSpaceIsInsideBoardGame(Point point) {
        return pointIsInsideBody(point, new Rectangle(0,
                0,
                Constants.DEFAULT_CELL_SIZE * (Constants.DEFAULT_GAME_TILES_HORIZONTALLY - 1)+Constants.DEFAULT_SPEED,
                Constants.DEFAULT_CELL_SIZE * (Constants.DEFAULT_GAME_TILES_VERTICALLY - 1)+Constants.DEFAULT_SPEED
        ));
    }


    private boolean pointIsInsideBody(Point aPoint, Rectangle rectangle) {
        return rectangle.contains(aPoint);
    }

    private boolean playerInterfereWithWall(Rectangle rectangle1, Rectangle rectangle2) {
        return rectangle1.intersects(rectangle2);
    }


    private boolean checkIfFieldIsObstacle(Player player, Point point) {
        Rectangle pointToBody = new Rectangle(point.x, point.y, Constants.DEFAULT_CELL_SIZE, Constants.DEFAULT_CELL_SIZE);
        for (Cell[] cell : boardGameInfo.getCells()) {
            for (Cell aCell : cell) {
                if (playerInterfereWithWall(pointToBody, aCell.getBody()) && !aCell.getType().walkable) {
                    System.out.println(aCell);
                    return true;
                }
            }
        }
   /*     if (checkIfBombForward(player, pointToBody)) {
            return true;}
   */
        return checkIfCrateForward(pointToBody);
    }

    private boolean checkIfCrateForward(Rectangle body) {
        for (Cell[] crateCells : getInfo().getCells()) {
            for (Cell crateCell : crateCells) {
                if (crateCell.getType() == CellType.CELL_CRATE && crateCell.getBody().intersects(body)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkIfCrateAtSpecificIndex(Point aCoord) {
        return boardGameInfo.getCells()[aCoord.x][aCoord.y].getType() ==CellType.CELL_CRATE ;
    }

    public boolean checkIfBombForward(Player player, Rectangle body) {
        Cell aCell = getPlayerPositionCell(player);
                     if(checkIfBombOnSpecificLocation(aCell.point) && player.isInsideBomb()) {
                        player.setInsideBomb(false);
                    }else
                        return aCell.getType() == CellType.CELL_BOMB && aCell.getBody().intersects(body) && !player.isInsideBomb();
        return false;
    }

    private boolean checkIfBombOnSpecificLocation(Point aCoord) {
        return getInfo().getCells()[aCoord.x][aCoord.y].getType() == CellType.CELL_BOMB;
    }

    public void plantBomb(Player player) {
        try {
            if(!player.isAlive()){
                System.out.println(player.getName()+" is dead");
                return;
            }
            for (int i = 0; i < boardGameInfo.getCells().length; i++) {
                for (int j = 0; j < boardGameInfo.getCells()[i].length; j++) {
                    if (boardGameInfo.getCells()[i][j].getBody().contains(getPlayerPosition(player))&& boardGameInfo.getCells()[i][j].getType() == CellType.CELL_EMPTY) {
                            boardGameInfo.getCells()[i][j].setType(CellType.CELL_BOMB);
                            setTimerCell(boardGameInfo.getCells()[i][j]);
                            System.out.println("Bomb Planted");
                            player.plantBomb();
                            player.restoreBombAmountEvent();
                    }
                }
            }
        } catch (NullPointerException exception) {
            System.err.println("Cant plant bomb for some reason");
        }
    }

    private Point getCellCordByBomb(Cell bombCell) {
        for (int i = 0; i < boardGameInfo.getCells().length; i++) {
            for (int j = 0; j < boardGameInfo.getCells()[i].length; j++) {
                if (boardGameInfo.getCells()[i][j].getPoint().equals(bombCell.point))
                    return new Point(i, j);
            }
        }
        return new Point(0, 0);
    }



    private void detonateBomb(Cell bombCell) {
        List<Cell> cellsToExplode = getCellsToExplosion(getCellCordByBomb(bombCell));
        cellsToExplode.forEach(cell -> {
            boardGameInfo.getCells()[cell.getPoint().x][cell.getPoint().y].setType(CellType.CELL_BOOM_CENTER);
            this.setEstinguishTimer(cell);
        }
        );
    }

    private void setEstinguishTimer(Cell cell){
        ScheduledExecutorService scheduler
                = Executors.newSingleThreadScheduledExecutor();

        Runnable task = new Runnable() {
            public void run() {
                cell.setType(CellType.CELL_EMPTY);
            }
        };

        int delay = Constants.BASIC_BOMB_EXPLOSION_BURNING_TIMER;
        scheduler.schedule(task, delay, TimeUnit.SECONDS);
        scheduler.shutdown();
    }
    private void setTimerCell(Cell cell){
            ScheduledExecutorService scheduler
                    = Executors.newSingleThreadScheduledExecutor();

            Runnable task = new Runnable() {
                public void run() {
                    cell.setType(CellType.CELL_EMPTY);
                    detonateBomb(cell);
                }
            };

            int delay = Constants.BASIC_BOMB_EXPLOSION_TIMER;
            scheduler.schedule(task, delay, TimeUnit.SECONDS);
            scheduler.shutdown();
    }
    public Cell[][] getCells() {
        return boardGameInfo.getCells();
    }

    private boolean checkIfFieldIsEmpty(Point point) {
        return !map.containsValue(point);
    }

    private List<Cell> getCellsToExplosion(Point point) {
        List<Cell> cells = new ArrayList<>(getNeighbors(getInfo().getCells()[point.x][point.y]));
        cells.add(getInfo().getCells()[point.x][point.y]);
       return cells;
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
        boolean north = true;
        boolean west = true;
        boolean east = true;
        boolean south = true;
        int row = 0;
        int col = 0;
                for (int rangeChecker = 1; rangeChecker < Constants.DEFAULT_RANGE; rangeChecker++) {
                        row = aCell.getPoint().x;
                        col = aCell.getPoint().y;
                        if ( west &&
                                row - rangeChecker >= 0 &&
                                boardGameInfo.getCells()[row-rangeChecker][col].getType()!= CellType.CELL_WALL &&
                                boardGameInfo.getCells()[row-rangeChecker][col].getType()!= CellType.CELL_BOMB) {
                            neighbors.add(boardGameInfo.getCells()[row - rangeChecker][col]);
                            if (checkIfCrateAtSpecificIndex(new Point(row - rangeChecker, col))) {
                                west = false;
                            }
                        } else
                            west = false;
                        if (north &&
                                col - rangeChecker >= 0 &&
                                boardGameInfo.getCells()[row][col - rangeChecker].getType()!= CellType.CELL_WALL &&
                                boardGameInfo.getCells()[row][col - rangeChecker].getType()!= CellType.CELL_BOMB) {
                            neighbors.add(boardGameInfo.getCells()[row][col - rangeChecker]);
                            if (checkIfCrateAtSpecificIndex(new Point(row, col - rangeChecker))){
                                north = false;
                            }
                        } else
                            north = false;
                        if (east &&
                                row + rangeChecker < Constants.DEFAULT_GAME_TILES_HORIZONTALLY &&
                                boardGameInfo.getCells()[row + rangeChecker][col].getType() != CellType.CELL_WALL &&
                                boardGameInfo.getCells()[row + rangeChecker][col].getType() != CellType.CELL_BOMB) {
                            neighbors.add(boardGameInfo.getCells()[row + rangeChecker][col]);
                            if (checkIfCrateAtSpecificIndex(new Point(row + rangeChecker, col))) {
                                east = false;
                            }
                        } else
                            east = false;
                        if (south &&
                                col + rangeChecker < Constants.DEFAULT_GAME_TILES_VERTICALLY &&
                                boardGameInfo.getCells()[row][col + rangeChecker].getType() != CellType.CELL_WALL &&
                                boardGameInfo.getCells()[row][col + rangeChecker].getType() != CellType.CELL_BOMB ) {
                            neighbors.add(boardGameInfo.getCells()[row][col + rangeChecker]);
                            if (checkIfCrateAtSpecificIndex(new Point(row, col + rangeChecker))) {
                                south = false;
                            }
                        } else
                            south = false;
                    }
        return neighbors;
    }
}
