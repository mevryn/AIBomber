package pl.dszi.player;

import pl.dszi.board.BoardGame;
import pl.dszi.board.Direction;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ManualPlayerController  implements PlayerController{

    private boolean manual=true;

    public boolean checkIfManual() {
        return manual;
    }

    @Override
    public boolean setPlayer(Player player) {
        return false;
    }
}
