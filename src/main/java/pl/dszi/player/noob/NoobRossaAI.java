package pl.dszi.player.noob;

import pl.dszi.board.BoardGame;
import pl.dszi.engine.constant.Constant;
import pl.dszi.player.Player;

import java.awt.*;

public class NoobRossaAI {


    public boolean shouldPlantBomb(BoardGame boardGame, Player player) {
        if (boardGame.checkIfBombForward(player, new Rectangle(boardGame.getPlayerPosition(player).x, boardGame.getPlayerPosition(player).y, Constant.DEFAULT_CELL_SIZE, Constant.DEFAULT_CELL_SIZE))) {
            return false;
        } else if (boardGame.getPlayerPositionCellByCenter(player).getType().walkable) {
            if (boardGame.checkIfNeighborIsCrate(boardGame.getPlayerPositionCellByCenter(player))) {
                return player.getBombAmount() > player.getBombActuallyTicking();
            }
            return false;
        } else {
            return false;
        }
    }


}
