package pl.dszi.engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.dszi.board.BoardGame;
import pl.dszi.board.Cell;
import pl.dszi.engine.constant.Constants;
import pl.dszi.player.ManualPlayerController;
import pl.dszi.player.Player;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;

public class BattleEngineTest {
    private Player player1;
    private Player player2;
    private BoardGame boardGame;
    @BeforeEach
    void init(){
        String NOT_IMPORTNANT_NAME="player";
        int hp = 3;
        boardGame = new BoardGame(new Cell[Constants.DEFAULT_GAME_TILES_HORIZONTALLY][Constants.DEFAULT_GAME_TILES_VERTICALLY]);
        player1 = new Player(NOT_IMPORTNANT_NAME,hp,new ManualPlayerController());
    }

    @Test
    void playerDieAfterReachingZeroLife() {
        Point NOT_IMPORTANT_POINT = new Point(0,0);
        boardGame.put(player1,NOT_IMPORTANT_POINT);
        player1.damagePlayer();
        player1.damagePlayer();
        player1.damagePlayer();
        player1.setAlive();
        assertTrue(!player1.isAlive());
    }
}
