package pl.dszi.player.noob;

import pl.dszi.board.BoardGame;
import pl.dszi.board.Cell;
import pl.dszi.board.CellType;
import pl.dszi.board.Direction;
import pl.dszi.engine.Game;
import pl.dszi.engine.GameStatus;
import pl.dszi.player.Player;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class NoobPlayerController {
    private BoardGame boardGame;
    private Point playerLocation;
    private List<Cell> way = new ArrayList<>();
    private Astar astar;
    private Set<Cell> bombs = new HashSet<>();


    private int actionCounter = 0;

    public NoobPlayerController(BoardGame boardGame, NoobRossaAI noobRossaAI) {
        this.boardGame = boardGame;
    }

    public void AIPlaning(Player player) {
        playerLocation = boardGame.getPlayerPosition(player);
        astar = new Astar(boardGame.getCells());
        if (way.size() == 0)
            way = astar.chooseBestWay(boardGame.getPlayerPositionCell(player), boardGame.getPlayerPositionCell(getClosestPlayer(player)));

        if (!playerLocation.equals(getClosestCellToEnemy(boardGame.getPlayerPositionCell(getClosestPlayer(player)), player).getBody().getLocation())) {
            way = astar.chooseBestWay(boardGame.getPlayerPositionCell(player), getClosestCellToEnemy(boardGame.getPlayerPositionCell(getClosestPlayer(player)), player));
            if (way.size() > 0 && playerLocation.equals(way.get(0).getBody().getLocation())) {
                way.remove(0);
                if (way.size() > 0)
                    makeAMove(way.get(0), player);
            } else if (way.size() > 0)
                makeAMove(way.get(0), player);
        }
        if (playerLocation.equals(getClosestCellToEnemy(boardGame.getPlayerPositionCell(getClosestPlayer(player)), player).getBody().getLocation())) {
            if (boardGame.plantBomb(player))
                actionCounter = actionCounter + 5;
        }
    }

    private Cell getClosestCellToEnemy(Cell closestPlayerCell, Player player) {
        int min = Integer.MAX_VALUE;
        int distance = 0;
        Cell returnCell = closestPlayerCell;
        List<Node> closedSetList = new ArrayList<>(astar.getClosedSet());
        closedSetList.sort(Comparator.comparingInt(o -> o.getCell().getPoint().x));
        for (Node node : closedSetList) {
            distance = Distances.returnManhattaDistanceOfTwoCells(node.getCell().getPoint(), closestPlayerCell.getPoint());
            if (distance < min) {
                returnCell = node.getCell();
                min = distance;
            }
        }
        return returnCell;
    }

    private void getBombs() {
        bombs = boardGame.getInfo().getAllBombs();
    }

    private boolean checkIfPlayerInRangeOfExplosions(Player player) {
        if (bombs.size() != 0)
            bombs.clear();
        getBombs();
        for (Cell bomb : bombs) {
            Set<Cell> explosionBombRange = boardGame.getAccessibleNeighbors(bomb, 4);
            if (explosionBombRange.stream().anyMatch(e -> e.getBody().intersects(boardGame.getPlayerBody(player)))) {
                return true;
            }
        }
        return false;
    }

    private Cell getClosestSafeCell(Player player) {
        Set<Node> safeCellsNode = astar.getClosedSet().stream().filter(node -> node.getCell().getType() != CellType.CELL_BOOM_CENTER).collect(Collectors.toSet());
        Set<Cell> safeCells = new HashSet<>();
        safeCellsNode.forEach(node -> safeCells.add(node.getCell()));
        Set<Cell> bombs = boardGame.getInfo().getAllBombs();
        for (Cell bomb : bombs) {
            Set<Cell> explosionBombRange = boardGame.getAccessibleNeighbors(bomb, 4);
            safeCells.removeAll(explosionBombRange);
            safeCells.remove(bomb);
        }
        return getClosestCellFromCollection(player, safeCells);
    }


    private Cell getClosestCellFromCollection(Player player, Collection<Cell> cells) {
        int min = Integer.MAX_VALUE;
        Cell returnCell = boardGame.getPlayerPositionCell(player);
        for (Cell cell : cells) {
            int distance = Distances.returnManhattaDistanceOfTwoCells(cell.getPoint(), boardGame.getPlayerPositionCell(player).getPoint());
            if (distance < min) {
                min = distance;
                returnCell = cell;
            }
        }
        return returnCell;
    }

    private Player getClosestPlayer(Player player) {
        int min = Integer.MAX_VALUE;
        Player returnPlayer = player;
        for (Player playerEntry : boardGame.getMap().keySet()) {
            if (!playerEntry.equals(player)) {
                int distance = Distances.returnManhattaDistanceOfTwoCells(playerLocation, boardGame.getMap().get(playerEntry));
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

    public void plantBomb(Player player) {
        boardGame.plantBomb(player);
    }

    public boolean checkIfManual() {
        return false;
    }

    public int getActionCounter() {
        return actionCounter;
    }

    public void setActionCounter(int actionCounter) {
        this.actionCounter = actionCounter;
    }

}

