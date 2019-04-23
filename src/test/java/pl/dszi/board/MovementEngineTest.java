package pl.dszi.board;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import pl.dszi.player.Player;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class MovementEngineTest {
    private Player player1;
    private Player player2;
    private BoardGame boardGame;

    @BeforeEach
    void init() {
        Integer NOT_IMPORTANT_HP = 3;
        player1 = new Player(NOT_IMPORTANT_HP);
        player2 = new Player(NOT_IMPORTANT_HP);
        boardGame = new BoardGame();
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
