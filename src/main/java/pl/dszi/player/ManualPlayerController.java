package pl.dszi.player;

import pl.dszi.board.Cell;
import pl.dszi.player.noob.NoobAI;

public class ManualPlayerController implements PlayerController {

    private boolean manual = true;

    public boolean checkIfManual() {
        return manual;
    }

    @Override
    public void makeAMove(Cell cell) {

    }

    @Override
    public void pathFinding() {

    }

    @Override
    public NoobAI getNoobAI() {
        return null;
    }

    @Override
    public void plantBomb() {

    }

    @Override
    public void setPlayer(Player player) {
    }
}
