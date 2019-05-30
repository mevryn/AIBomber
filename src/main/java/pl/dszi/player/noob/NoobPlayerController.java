package pl.dszi.player.noob;

import pl.dszi.board.BoardGame;
import pl.dszi.board.Cell;
import pl.dszi.board.Direction;
import pl.dszi.player.Player;
import pl.dszi.player.PlayerController;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class NoobPlayerController implements PlayerController {
    private boolean manual = false;
    private BoardGame boardGame;
    private Astar astar;
    private Point playerLocation;
    private Direction direction;
    private List<Cell> way = new ArrayList<>();
    private NoobAI noobAI;

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
        this.astar = new Astar(boardGame.getCells());
        if (noobAI.makeDecision(boardGame,player)) {
            boardGame.plantBomb(player);
        }
        if(boardGame.getPlayerPositionCell(player).getBody().getLocation().equals(playerLocation))
            way = astar.chooseBestWay(boardGame.getPlayerPositionCell(player), boardGame.getPlayerPositionCell(getClosestPlayer(player)));
        if (way.size() > 0 && playerLocation.equals(way.get(0).getBody().getLocation())) {
            way.remove(0);
            if(way.size()>0)
            makeAMove(way.get(0),player);
        } else if (way.size() > 0)
            makeAMove(way.get(0),player);
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
        return manual;
    }

}

