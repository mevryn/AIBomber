package pl.dszi.player;

import pl.dszi.board.Cell;
import pl.dszi.player.noob.NoobAI;

public interface PlayerController {
    void setPlayer(Player player);

    boolean checkIfManual();

    void makeAMove(Cell cell);

    void plantBomb();

    void pathFinding();

    NoobAI getNoobAI();
}
