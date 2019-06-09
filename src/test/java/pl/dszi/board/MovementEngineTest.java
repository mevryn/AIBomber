package pl.dszi.board;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import pl.dszi.engine.constant.Constants;
import pl.dszi.player.ManualPlayerController;
import pl.dszi.player.Player;
import pl.dszi.player.noob.NoobPlayerController;
import pl.dszi.player.noob.NoobRossaAI;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class MovementEngineTest {
    private Player player1;
    private Player player2;
    private BoardGame boardGame;
    @BeforeEach
    void init() {
        String NOT_IMPORTNANT_NAME="player";
        int NOT_IMPORTANT_HP = 3;
        boardGame = new BoardGame(new Cell[Constants.DEFAULT_GAME_TILES_HORIZONTALLY][Constants.DEFAULT_GAME_TILES_VERTICALLY]);
        player1 = new Player(NOT_IMPORTNANT_NAME,NOT_IMPORTANT_HP,new ManualPlayerController());
        player2 = new Player(NOT_IMPORTNANT_NAME,NOT_IMPORTANT_HP,new NoobPlayerController(boardGame,new NoobRossaAI()));
  }

    @Test
    void cannotPutPlayerOnTakenPlace() {
        Point firstPlayerPosition = new Point(0, 1) ;
        boolean firstPlayedSuccessfullyPositioned =boardGame.put(player1, firstPlayerPosition);
        assertTrue(firstPlayedSuccessfullyPositioned);
        boolean secondPlayerSuccessfullyPositioned =boardGame.put(player2, firstPlayerPosition);
        assertFalse(secondPlayerSuccessfullyPositioned);
    }
    @Test
    void playerShouldMoveOnePointToEast() {
        boardGame.put(player1,new Point(0,0));
        boardGame.move(player1,Direction.EAST);
        assertEquals(new Point(4, 0), boardGame.getPlayerPosition(player1));
    }
    @Test
    void playerCantMoveOutOfBoard(){
        boardGame.put(player1,new Point(0,0));
        boardGame.move(player1,Direction.NORTH);
        assertEquals(new Point(0, 0), boardGame.getPlayerPosition(player1));
    }

    @Test
    void playerCantMoverAfterReachingZeroLife() {
        Point NOT_IMPORTANT_POINT = new Point(0,0);
        boardGame.put(player1,NOT_IMPORTANT_POINT);
        player1.damagePlayer();
        player1.damagePlayer();
        player1.damagePlayer();
        Point oldPosition = boardGame.getPlayerPosition(player1);
        boardGame.move(player1,Direction.SOUTH);
        Point newPosition = boardGame.getPlayerPosition(player1);
        assertEquals(oldPosition,newPosition);
    }
}
