package pl.dszi.player.noob;

import pl.dszi.board.BoardGame;
import pl.dszi.player.Player;

public interface NoobAI {
    boolean shouldPlantBomb(BoardGame boardGame,Player player);
}
