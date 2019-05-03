package pl.dszi.player.noob;

import pl.dszi.board.BoardGame;
import pl.dszi.board.Cell;
import pl.dszi.board.Direction;
import pl.dszi.engine.Constants;
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
    private Cell targetPlayerPositionCell;
    private List<Cell> way= new ArrayList<>();
    public NoobPlayerController(BoardGame boardGame) {
        this.boardGame = boardGame;
    }
    
    @Override
    public void pathFinding(){
        this.player= boardGame.getPlayerByName(Constants.PLAYER_2_NAME);
        playerLocation = boardGame.getPlayerPosition(player);
        this.astar = new Astar(boardGame.getCells());
        if(way.size()==0){
            way =astar.chooseBestWay(boardGame.getPlayerPositionCell(player),boardGame.getPlayerPositionCell(boardGame.getPlayerByName(Constants.PLAYER_1_NAME)));
            System.out.println("dupa");
        }
        if(way.size()>0)
            if (way.get(0).getBody().getLocation().equals(boardGame.getPlayerPosition(player))) {
                System.out.println(way);
                way.remove(0);
            }
            else
                makeAMove(way.get(0));
            }

    public void makeAMove(Cell cell) {
            if (playerLocation.x > cell.getPoint().x ) {
                if(!boardGame.move(player, Direction.WEST))
                   way = astar.chooseBestWay(boardGame.getPlayerPositionCell(this.player),boardGame.getPlayerPositionCell(boardGame.getPlayerByName(Constants.PLAYER_1_NAME)));
            } else if (playerLocation.x < cell.getPoint().x) {
                if(!boardGame.move(player, Direction.EAST))
                    way = astar.chooseBestWay(boardGame.getPlayerPositionCell(this.player),boardGame.getPlayerPositionCell(boardGame.getPlayerByName(Constants.PLAYER_1_NAME)));
            } else if (playerLocation.y > cell.getPoint().y ) {
                if(!boardGame.move(player, Direction.NORTH))
                    way = astar.chooseBestWay(boardGame.getPlayerPositionCell(this.player),boardGame.getPlayerPositionCell(boardGame.getPlayerByName(Constants.PLAYER_1_NAME)));
            } else if (playerLocation.y < cell.getPoint().y ) {
                if(!boardGame.move(player, Direction.SOUTH))
                    way = astar.chooseBestWay(boardGame.getPlayerPositionCell(this.player),boardGame.getPlayerPositionCell(boardGame.getPlayerByName(Constants.PLAYER_1_NAME)));
            }
    }

    public void setAstarWay(){

    }
    @Override
    public void plantBomb() {

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

