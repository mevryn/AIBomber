package pl.dszi.engine;

import pl.dszi.board.BoardGame;
import pl.dszi.player.Player;

import java.util.*;

public class Holder {
    private List<Player> playerSet = new ArrayList<>();

    private BoardGame boardGame = new BoardGame();

    public Holder() {
        int playerHp=3;
        playerSet.add(new Player(playerHp));
        playerSet.add(new Player(playerHp));
        playerSet.add(new Player(playerHp));
        playerSet.add(new Player(playerHp));
    }
}
