package pl.dszi.board;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import pl.dszi.engine.Constants;
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
        boardGame = new BoardGame(new Cell[Constants.DEFAULT_GAME_TILES_HORIZONTALLY][Constants.DEFAULT_GAME_TILES_VERTICALLY]);
        player1 = new Player(NOT_IMPORTNANT_NAME,NOT_IMPORTANT_HP,new NoobPlayerController(boardGame));
        player2 = new Player(NOT_IMPORTNANT_NAME,NOT_IMPORTANT_HP,new NoobPlayerController(boardGame));
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
        boardGame.put(player2,new Point(64,64));
        boardGame.move(player1,Direction.SOUTH);
        assertEquals(new Point(0, 0), boardGame.getPlayerPosition(player1));
    }

    @Test
    void gameDimensionNeedToBeDevidedBySpeed(){

        boolean divisible;
        if(Constants.DEFAULT_GAME_WIDTH%Constants.DEFAULT_SPEED==0 && Constants.DEFAULT_GAME_HEIGHT%Constants.DEFAULT_SPEED==0) {
            divisible = true;
        } else
        {
            divisible = false;
        }
        assertTrue(divisible);
    }
}
