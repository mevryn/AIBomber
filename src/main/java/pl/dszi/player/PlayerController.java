package pl.dszi.player;

import pl.dszi.board.Cell;
import pl.dszi.player.noob.NoobAI;

public interface PlayerController {

    boolean checkIfManual();

    void makeAMove(Cell cell,Player player);

    void plantBomb(Player player);

    void pathFinding(Player player);

    NoobAI getNoobAI();
}
