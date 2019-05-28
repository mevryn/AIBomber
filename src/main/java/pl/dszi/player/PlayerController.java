package pl.dszi.player;

import pl.dszi.board.Cell;

public interface PlayerController {
    void setPlayer(Player player);

    boolean checkIfManual();

    void makeAMove(Cell cell);

    void plantBomb();

    void pathFinding();
}
