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
    private Player player;
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
    public void pathFinding() {
        setPlayer();
        playerLocation = boardGame.getPlayerPosition(player);
        this.astar = new Astar(boardGame.getCells());
        if (noobAI.makeDecision(boardGame)) {
            boardGame.plantBomb(player);
        }
        if(boardGame.getPlayerPositionCell(player).getBody().getLocation().equals(playerLocation))
            way = astar.chooseBestWay(boardGame.getPlayerPositionCell(player), boardGame.getPlayerPositionCell(getClosestPlayer()));
        if (way.size() > 0 && playerLocation.equals(way.get(0).getBody().getLocation())) {
            way.remove(0);
            if(way.size()>0)
            makeAMove(way.get(0));
        } else if (way.size() > 0)
            makeAMove(way.get(0));
    }


    private void setPlayer() {
        Optional<Player> playerOptional = boardGame.getMap().keySet().stream().filter(player1 -> player1.getPlayerController().equals(this)).findAny();
        playerOptional.ifPresent(this::setPlayer);
        playerOptional.ifPresent(noobAI::setPlayer);
    }

    private Player getClosestPlayer() {
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

    public void makeAMove(Cell cell) {
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
    public void plantBomb() {
        boardGame.plantBomb(player);
    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public boolean checkIfManual() {
        return manual;
    }

}

