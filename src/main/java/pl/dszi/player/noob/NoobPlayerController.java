package pl.dszi.player.noob;

import pl.dszi.board.BoardGame;
import pl.dszi.board.Cell;
import pl.dszi.board.Direction;
import pl.dszi.engine.Constants;
import pl.dszi.player.Player;
import pl.dszi.player.PlayerController;

import java.awt.*;

public class NoobPlayerController implements PlayerController {
    private boolean manual = false;
    private BoardGame boardGame;

    public NoobPlayerController(BoardGame boardGame) {
        this.boardGame = boardGame;

    }

    public void makeAMove(Cell cell) {
        Player player = boardGame.getPlayerByName(Constants.PLAYER_2_NAME);
        Point location = this.boardGame.getPlayerPosition(boardGame.getPlayerByName(Constants.PLAYER_2_NAME));
        if (location != cell.getPoint()) {
                if (location.x > cell.getPoint().x) {
                    boardGame.move(player, Direction.WEST);
                } else if (location.x < cell.getPoint().x) {
                    boardGame.move(player, Direction.EAST);
                } else if (location.y > cell.getPoint().y) {
                    boardGame.move(player, Direction.NORTH);
                } else if (location.y < cell.getPoint().y) {
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
