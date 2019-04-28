package pl.dszi.player;

import pl.dszi.board.Cell;

public interface PlayerController {
    boolean setPlayer(Player player);
    boolean checkIfManual();
    public void makeAMove(Cell cell);
}
