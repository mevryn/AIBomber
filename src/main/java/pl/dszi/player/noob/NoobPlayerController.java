package pl.dszi.player.noob;

import pl.dszi.board.BoardGame;
import pl.dszi.player.Player;
import pl.dszi.player.PlayerController;

public class NoobPlayerController implements  PlayerController {
    private Player player;
    private boolean manual = false;

    private BoardGame boardGame;
    @Override
    public boolean setPlayer(Player player) {
        return false;
    }

    @Override
    public boolean checkIfManual() {
        return manual;
    }

}
