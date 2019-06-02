package pl.dszi.player.noob;

import pl.dszi.board.BoardGame;
import pl.dszi.board.Cell;
import pl.dszi.board.CellType;
import pl.dszi.board.Direction;
import pl.dszi.engine.constant.Constants;
import pl.dszi.player.Player;
import pl.dszi.player.PlayerController;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class NoobPlayerController implements PlayerController {
    private BoardGame boardGame;
    private Point playerLocation;
    private Direction direction;
    private List<Cell> way = new ArrayList<>();
    private NoobAI noobAI;
    private Astar astar;

    public NoobPlayerController(BoardGame boardGame, NoobAI noobAI) {
        this.boardGame = boardGame;
        this.noobAI = noobAI;
        this.direction = Direction.SOUTH;
    }

    @Override
    public NoobAI getNoobAI() {
        return noobAI;
    }

    @Override
    public void pathFinding(Player player) {
        playerLocation = boardGame.getPlayerPosition(player);
        astar = new Astar(boardGame.getCells());
        if (noobAI.shouldPlantBomb(boardGame,player)) {
            boardGame.plantBomb(player);
        }

        if (way.size() > 0 && playerLocation.equals(way.get(0).getBody().getLocation())) {
            way.remove(0);
            if(way.size()>0)
            makeAMove(way.get(0),player);
        } else if (way.size() > 0)
            makeAMove(way.get(0),player);
            way = astar.chooseBestWay(boardGame.getPlayerPositionCell(player), boardGame.getPlayerPositionCell(getClosestPlayer(player)));
        if(checkIfPlayerInRangeOfExplosions(player)){
            way = astar.chooseBestWay(boardGame.getPlayerPositionCell(player),getClosestSafeCell(player));
        }
        else if (way.size()==0 && !boardGame.getPlayerPositionCell(player).equals(boardGame.getPlayerPositionCell(getClosestPlayer(player)))){
            way = astar.chooseBestWay(boardGame.getPlayerPositionCell(player),getClosestCellToEnemy(boardGame.getPlayerPositionCell(getClosestPlayer(player))));
        }
        System.out.println(way);
    }

    private Cell getClosestCellToEnemy (Cell closestPlayerCell){
        int min = Integer.MAX_VALUE;
        Cell returnCell = closestPlayerCell;
        for(Node node:astar.getClosedSet()){
            int distance = Distances.returnManhattaDistanceOfTwoCells(node.getCell().getPoint(),closestPlayerCell.getPoint());
                if(distance<min){
                    returnCell = node.getCell();
                    min = distance;
                }
            }
            return  returnCell;
    }
    private boolean checkIfPlayerInRangeOfExplosions(Player player){
        for (Cell bomb: boardGame.getInfo().getAllBombs()) {
            Set<Cell> explosionBombRange = boardGame.getAccessibleNeighbors(bomb,4);
            if(explosionBombRange.stream().anyMatch(e-> e.getBody().intersects(boardGame.getPlayerBody(player)))){
                return true;
            }
        }
        return false;
    }

    private Cell getClosestSafeCell(Player player){
        int min = Integer.MAX_VALUE;
        Cell returnCell = boardGame.getPlayerPositionCellByCenter(player);
        Set<Node> safeCellsNode = astar.getClosedSet().stream().filter(node -> node.getCell().getType()!=CellType.CELL_BOOM_CENTER).collect(Collectors.toSet());
        Set<Cell> safeCells = new HashSet<>();
        safeCellsNode.forEach(node -> safeCells.add(node.getCell()));
        Set<Cell> bombs = boardGame.getInfo().getAllBombs();
        for(Cell bomb:bombs){
            Set<Cell> explosionBombRange = boardGame.getAccessibleNeighbors(bomb,4);
            safeCells.removeAll(explosionBombRange);
            safeCells.remove(bomb);
        }

        return getClosestCellFromCollection(player,safeCells);
    }


    private Cell getClosestCellFromCollection(Player player,Collection<Cell> cells){
        int min = Integer.MAX_VALUE;
        Cell returnCell= boardGame.getPlayerPositionCellByCenter(player);
        for(Cell cell:cells){
            int distance = Distances.returnManhattaDistanceOfTwoCells(cell.getPoint(),boardGame.getPlayerPositionCellByCenter(player).getPoint());
            if(distance<min){
                min = distance;
                returnCell = cell;
            }
        }
        return returnCell;
    }
    private Player getClosestPlayer(Player player) {
        int min = Integer.MAX_VALUE;
        Player returnPlayer= player;
        for(Player playerEntry:boardGame.getMap().keySet()){
            if(!playerEntry.equals(player)){
               int distance = Distances.returnManhattaDistanceOfTwoCells(playerLocation,boardGame.getMap().get(playerEntry));
                if(distance<min){
                    returnPlayer = playerEntry;
                    min = distance;
                }
            }
        }
       return returnPlayer;
    }

    public void makeAMove(Cell cell,Player player) {
        if (playerLocation.x > cell.getBody().x) {
            boardGame.move(player, Direction.WEST);
        } else if (playerLocation.x < cell.getBody().x) {
            boardGame.move(player, Direction.EAST);
        } else if (playerLocation.y > cell.getBody().y) {
            boardGame.move(player, Direction.NORTH);
        } else if (playerLocation.y < cell.getBody().y) {
            boardGame.move(player, Direction.SOUTH);
        }
    }

    @Override
    public void plantBomb(Player player) {
        boardGame.plantBomb(player);
    }

    @Override
    public boolean checkIfManual() {
        return false;
    }



}

