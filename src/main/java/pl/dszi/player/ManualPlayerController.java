package pl.dszi.player;

import pl.dszi.board.Cell;

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
    public void plantBomb() {

    }

    @Override
    public void setPlayer(Player player) {
    }
}
