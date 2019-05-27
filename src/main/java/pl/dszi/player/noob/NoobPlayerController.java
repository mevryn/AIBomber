package pl.dszi.player.noob;

import pl.dszi.board.BoardGame;
import pl.dszi.board.Cell;
import pl.dszi.board.Direction;
import pl.dszi.engine.constant.Constants;
import pl.dszi.player.Player;
import pl.dszi.player.PlayerController;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class NoobPlayerController implements PlayerController {
    private boolean manual = false;
    private BoardGame boardGame;
    private Astar astar;
    private Point playerLocation;
    private Player player;
    private Direction direction;
    private List<Cell> way= new ArrayList<>();
    private NoobAI noobAI;
    public NoobPlayerController(BoardGame boardGame,NoobAI noobAI) {
        this.noobAI = noobAI;
        this.boardGame = boardGame;
        this.direction = Direction.SOUTH;
        this.noobAI.setPlayer(player);

    }
    @Override
    public void pathFinding(){
        this.player= boardGame.getPlayerByName(Constants.PLAYER_2_NAME);
        playerLocation = boardGame.getPlayerPosition(player);
        this.astar = new Astar(boardGame.getCells());
        if(noobAI.makeDecision(boardGame)){
            boardGame.plantBomb(player);
        }

        if(way.size()==0)
            way = astar.chooseBestWay(boardGame.getPlayerPositionCell(player),boardGame.getCells()[9][9]);
        if(way.size()>0&&playerLocation.equals(way.get(0).getBody().getLocation())) {
            way.remove(0);
        }else
            if(way.size()>0)
                makeAMove(way.get(0));
    }


    public void makeAMove(Cell cell) {
            if (playerLocation.x > cell.getBody().x ) {
                boardGame.move(player, Direction.WEST);
            } else if (playerLocation.x < cell.getBody().x) {
                boardGame.move(player, Direction.EAST);
            } else if (playerLocation.y > cell.getBody().y ) {
                boardGame.move(player, Direction.NORTH);
            } else if (playerLocation.y < cell.getBody().y ) {
                boardGame.move(player, Direction.SOUTH);
            }
    }

    @Override
    public void plantBomb() {
        boardGame.plantBomb(player);
    }

    @Override
    public boolean setPlayer(Player player) {
        return false;
    }

    @Override
    public boolean checkIfManual() {
        return manual;
    }

}

