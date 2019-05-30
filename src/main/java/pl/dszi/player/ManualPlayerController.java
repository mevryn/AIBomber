package pl.dszi.player;

import pl.dszi.board.Cell;
import pl.dszi.player.noob.NoobAI;

public class ManualPlayerController implements PlayerController {

    private boolean manual = true;

    public boolean checkIfManual() {
        return manual;
    }

    @Override
    public void makeAMove(Cell cell, Player player) {

    }

    @Override
    public void plantBomb(Player player) {

    }

    @Override
    public void pathFinding(Player player) {

    }


    @Override
    public NoobAI getNoobAI() {
        return null;
    }


}
