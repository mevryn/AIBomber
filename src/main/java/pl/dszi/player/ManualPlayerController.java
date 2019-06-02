package pl.dszi.player;

import pl.dszi.board.Cell;
import pl.dszi.player.noob.NoobAI;

public class ManualPlayerController implements PlayerController {

    public boolean checkIfManual() {
        return true;
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
