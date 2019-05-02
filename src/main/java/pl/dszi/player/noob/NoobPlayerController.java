package pl.dszi.player.noob;

import pl.dszi.board.BoardGame;
import pl.dszi.board.Cell;
import pl.dszi.board.Direction;
import pl.dszi.engine.Constants;
import pl.dszi.player.Player;
import pl.dszi.player.PlayerController;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;

public class NoobPlayerController implements PlayerController {
    private boolean manual = false;
    private BoardGame boardGame;
    private List<Point> enemiesLocation;
    private Astar astar;
    private Thread aiPlayerThread;
    public NoobPlayerController(BoardGame boardGame) {
        this.enemiesLocation = new ArrayList<>();
        this.boardGame = boardGame;
        this.astar = new Astar(boardGame.getCells());
        this.aiPlayerThread = new Thread();
        this.aiPlayerThread.start();
    }


    public void makeAMove(Cell cell) {

        Player player = boardGame.getPlayerByName(Constants.PLAYER_2_NAME);
        Point location = this.boardGame.getPlayerPosition(boardGame.getPlayerByName(Constants.PLAYER_2_NAME));
        System.out.println(boardGame.getNeighbors(boardGame.getPlayerPositionCell(player)));
        astar.chooseBestWay(boardGame.getPlayerPositionCell(player),cell);
        if (location != cell.getPoint()) {
            if (location.x > cell.getPoint().x ) {
                if(!boardGame.move(player, Direction.WEST)){
                    boardGame.move(player,Direction.NORTH);
                }
            } else if (location.x < cell.getPoint().x) {
                boardGame.move(player, Direction.EAST);
            } else if (location.y > cell.getPoint().y ) {
                boardGame.move(player, Direction.NORTH);
            } else if (location.y < cell.getPoint().y ) {
                boardGame.move(player, Direction.SOUTH);
            }
        }
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
