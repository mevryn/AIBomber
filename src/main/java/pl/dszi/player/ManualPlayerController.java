package pl.dszi.player;

import pl.dszi.board.BoardGame;
import pl.dszi.board.Cell;
import pl.dszi.board.Direction;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ManualPlayerController  implements PlayerController{

    private boolean manual=true;

    public boolean checkIfManual() {
        return manual;
    }

    @Override
    public void makeAMove(Cell cell) {

    }

    @Override
    public boolean setPlayer(Player player) {
        return false;
    }
}
