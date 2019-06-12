package pl.dszi.player.noob;

import pl.dszi.board.BoardGame;
import pl.dszi.board.Cell;
import pl.dszi.board.CellType;
import pl.dszi.board.Direction;
import pl.dszi.engine.Game;
import pl.dszi.engine.GameStatus;
import pl.dszi.engine.constant.Constant;
import pl.dszi.player.Player;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class AIController {
    private BoardGame boardGame;
    private Point playerLocation;
    private List<Cell> way = new ArrayList<>();
    private Astar astar;
    private List<Cell> bombs = new ArrayList<>();
    private boolean runningFromBomb = false;

    private int actionCounter = 0;

    public AIController(BoardGame boardGame) {
        this.boardGame = boardGame;
        this.astar = new Astar(boardGame.getCells());

    }

    public void AIPlaning(Player player) {
        playerLocation = boardGame.getPlayerPosition(player);
        astar = new Astar(boardGame.getCells());
        if (way.size() == 0)
            way = astar.chooseBestWay(boardGame.getPlayerPositionCell(player), boardGame.getPlayerPositionCell(getClosestPlayer(player)));
        if (way.size()==0 && checkIfPlayerInRangeOfExplosions(player)) {
            way = astar.chooseBestWay(boardGame.getPlayerPositionCell(player), getClosestSafeCell(player));
            runningFromBomb=true;
        }else if (!runningFromBomb  &&  !playerLocation.equals(getClosestCellToEnemy(boardGame.getPlayerPositionCell(getClosestPlayer(player)),player).getBody().getLocation())) {
            way = astar.chooseBestWay(boardGame.getPlayerPositionCell(player), getClosestCellToEnemy(boardGame.getPlayerPositionCell(getClosestPlayer(player)),player));
        }else if(boardGame.getInfo().getAllSpecificCells(CellType.CELL_BOOM_CENTER).size()==0&&boardGame.getInfo().getAllSpecificCells(CellType.CELL_BOMB).size()==0){
            runningFromBomb=false;
        }
        if (way.size() > 0 && playerLocation.equals(way.get(0).getBody().getLocation())) {
            way.remove(0);
        }
        if (way.size() > 0)
            makeAMove(way.get(0), player);
        if (playerLocation.equals(getClosestCellToEnemy(boardGame.getPlayerPositionCell(getClosestPlayer(player)),player).getBody().getLocation())) {
            if (boardGame.plantBomb(player))
                actionCounter = actionCounter + 5;
        }
    }


    Cell getClosestCellToEnemy(Cell closestPlayerCell,Player player) {
        int min = Integer.MAX_VALUE;
        int distance;
        Cell returnCell = closestPlayerCell;
        List<Node> closedSetList = new ArrayList<>(astar.getClosedSet());
        closedSetList.sort(Comparator.comparingInt(o -> Distances.returnManhattanDistance(boardGame.getPlayerPosition(getClosestPlayer(player)),o.getCell().getBody().getLocation())));
        for (Node node : closedSetList) {
            distance = Distances.returnManhattanDistance(node.getCell().getPoint(), closestPlayerCell.getPoint());
            if (distance < min) {
                returnCell = node.getCell();
                min = distance;
            }
        }
        return returnCell;
    }


    private void getBombs() {
        bombs = boardGame.getInfo().getAllSpecificCells(CellType.CELL_BOMB);
    }

    private boolean checkIfPlayerInRangeOfExplosions(Player player) {
        if (bombs.size() != 0)
            bombs.clear();
        getBombs();
        for (Cell bomb : bombs) {
            List<Cell> explosionBombRange = boardGame.getAccessibleNeighbors(bomb, 4);
            if (explosionBombRange.stream().anyMatch(e -> e.getBody().intersects(boardGame.getPlayerBody(player)))) {
                return true;
            }
        }
        return false;
    }

    private Cell getClosestSafeCell(Player player) {
        Set<Node> safeCellsNode = astar.getClosedSet()
                .stream()
                .filter(node ->node.getCell().getType() != CellType.CELL_BOMB && node.getCell().getType() != CellType.CELL_BOOM_CENTER)
                .collect(Collectors.toSet());
        List<Cell> safeCells = new ArrayList<>();
        safeCellsNode.forEach(node -> safeCells.add(node.getCell()));
        safeCells.sort(Comparator.comparingInt(o -> Distances.returnManhattanDistance(playerLocation,o.getBody().getLocation())));
        for (Cell bomb : bombs) {
            List<Cell> explosionBombRange = boardGame.getAccessibleNeighbors(bomb, Constant.DEFAULT_EXPLOSION_RANGE);
            safeCells.removeAll(explosionBombRange);
            safeCells.remove(bomb);
        }
        System.out.println(getClosestCellFromCollection(player, safeCells));
        return getClosestCellFromCollection(player, safeCells);
    }


    private Cell getClosestCellFromCollection(Player player, List<Cell> cells) {
        int min = Integer.MAX_VALUE;
        Cell returnCell = boardGame.getPlayerPositionCell(player);
        for (Cell cell : cells) {
            int distance = Distances.returnManhattanDistance(cell.getPoint(), boardGame.getPlayerPositionCell(player).getPoint());
            if (distance < min) {
                min = distance;
                returnCell = cell;
            }
        }
        return returnCell;
    }

    Player getClosestPlayer(Player player) {
        int min = Integer.MAX_VALUE;
        Player returnPlayer = player;
        for (Player playerEntry : boardGame.getMap().keySet()) {
            if (!playerEntry.equals(player)) {
                int distance = Distances.returnManhattanDistance(boardGame.getPlayerPosition(player), boardGame.getMap().get(playerEntry));
                if (distance < min) {
                    returnPlayer = playerEntry;
                    min = distance;
                }
            }
        }
        return returnPlayer;
    }

    private void makeAMove(Cell cell, Player player) {
        if (Game.gameStatus != GameStatus.GENERATING) {
            if (playerLocation.x > cell.getBody().x && playerLocation.y == cell.getBody().y) {
                boardGame.move(player, Direction.WEST);
            } else if (playerLocation.x < cell.getBody().x && playerLocation.y == cell.getBody().y) {
                boardGame.move(player, Direction.EAST);
            } else if (playerLocation.y > cell.getBody().y && playerLocation.x == cell.getBody().x) {
                boardGame.move(player, Direction.NORTH);
            } else if (playerLocation.y < cell.getBody().y && playerLocation.x == cell.getBody().x) {
                boardGame.move(player, Direction.SOUTH);
            }
            actionCounter++;
        } else {
            if (playerLocation.x > cell.getBody().x && playerLocation.y == cell.getBody().y) {
                boardGame.move(player, Direction.GENWEST);
            } else if (playerLocation.x < cell.getBody().x && playerLocation.y == cell.getBody().y) {
                boardGame.move(player, Direction.GENEAST);
            } else if (playerLocation.y > cell.getBody().y && playerLocation.x == cell.getBody().x) {
                boardGame.move(player, Direction.GENNORTH);
            } else if (playerLocation.y < cell.getBody().y && playerLocation.x == cell.getBody().x) {
                boardGame.move(player, Direction.GENSOUTH);
            }
        }
    }

    public int getActionCounter() {
        return actionCounter;
    }

    public void setActionCounter(int actionCounter) {
        this.actionCounter = actionCounter;
    }

    Astar getAstar() {
        return astar;
    }
}

