package pl.dszi.player.noob;

import pl.dszi.board.BoardGame;
import pl.dszi.engine.constant.Constants;
import pl.dszi.player.Player;

import java.awt.*;

public class NoobRossaAI implements NoobAI {
    Player player;

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public boolean makeDecision(BoardGame boardGame) {
        player = boardGame.getPlayerByName(Constants.PLAYER_2_NAME);
        if(boardGame.checkIfBombForward(player,new Rectangle(boardGame.getPlayerPosition(player).x,boardGame.getPlayerPosition(player).y,Constants.DEFAULT_CELL_SIZE,Constants.DEFAULT_CELL_SIZE))){
            return false;
        }else if(boardGame.getPlayerPositionCell(player).getType().walkable){
            if(boardGame.checkIfNeighborIsCrate(boardGame.getPlayerPositionCell(player))){
                return player.getBombAmount() > player.getBombActualyTicking();
            }
            return false;
        }else{
            return false;
        }
    }
}
