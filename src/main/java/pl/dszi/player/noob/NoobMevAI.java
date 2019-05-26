package pl.dszi.player.noob;

import pl.dszi.board.BoardGame;
import pl.dszi.player.Player;

public class NoobMevAI implements NoobAI {

    private Player player;

    @Override
    public boolean makeDecision(BoardGame boardGame) {
        return false;
    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

}
