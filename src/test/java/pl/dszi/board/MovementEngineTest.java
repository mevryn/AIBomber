package pl.dszi.board;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import pl.dszi.player.Player;
import pl.dszi.player.noob.NoobPlayerController;

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
        int NOT_IMPORTANT_SIZE=700;
        boardGame = new BoardGame(NOT_IMPORTANT_SIZE,NOT_IMPORTANT_SIZE,null);
        player1 = new Player(NOT_IMPORTNANT_NAME,NOT_IMPORTANT_HP,new NoobPlayerController());
        player2 = new Player(NOT_IMPORTNANT_NAME,NOT_IMPORTANT_HP,new NoobPlayerController());
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
        assertEquals(new Point(1, 0), boardGame.getPlayerPosition(player1));
    }
    @Test
    void playerCantMoveOutOfBoard(){
        boardGame.put(player1,new Point(0,0));
        boardGame.move(player1,Direction.NORTH);
        assertEquals(new Point(0, 0), boardGame.getPlayerPosition(player1));
    }
    @Test
    void playerCantMoveToTakenPlace(){
        boardGame.put(player1,new Point(0,0));
        boardGame.put(player2,new Point(0,1));
        boardGame.move(player1,Direction.SOUTH);
        assertEquals(new Point(0, 0), boardGame.getPlayerPosition(player1));
    }
}
