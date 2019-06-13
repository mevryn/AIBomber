package pl.dszi.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.dszi.engine.GameStatus;
import pl.dszi.engine.constant.Constant;
import pl.dszi.player.Player;
import pl.dszi.player.noob.AIController;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class MovementEngineTest {
    private Player player1;
    private Player player2;
    private BoardGame boardGame;

    @BeforeEach
    void init() {
        String NOT_IMPORTNANT_NAME = "player";
        int NOT_IMPORTANT_HP = 3;
        boardGame = new BoardGame(new Cell[Constant.DEFAULT_GAME_TILES_HORIZONTALLY][Constant.DEFAULT_GAME_TILES_VERTICALLY]);
        player1 = new Player(NOT_IMPORTNANT_NAME, NOT_IMPORTANT_HP);
        player2 = new Player(NOT_IMPORTNANT_NAME, NOT_IMPORTANT_HP, new AIController(boardGame));
    }

    @Test
    void cannotPutPlayerOnTakenPlace() {
        Point firstPlayerPosition = new Point(0, 1);
        boolean firstPlayedSuccessfullyPositioned = boardGame.put(player1, firstPlayerPosition);
        assertTrue(firstPlayedSuccessfullyPositioned);
        boolean secondPlayerSuccessfullyPositioned = boardGame.put(player2, firstPlayerPosition);
        assertFalse(secondPlayerSuccessfullyPositioned);
    }

    @Test
    void playerShouldMoveOnePointToEast() {
        boardGame.put(player1, new Point(0, 0));
        boardGame.move(player1, Direction.EAST);
        assertEquals(new Point(Constant.DEFAULT_SPEED, 0), boardGame.getPlayerPosition(player1));
    }

    @Test
    void playerShouldMoveOneCellToEastWhileGenerating() {
        boardGame.put(player1, new Point(0, 0));
        boardGame.move(player1, Direction.GENEAST);
        assertEquals(new Point(64, 0), boardGame.getPlayerPosition(player1));
    }

    @Test
    void playerCantMoveOutOfBoard() {
        boardGame.put(player1, new Point(0, 0));
        boardGame.move(player1, Direction.NORTH);
        assertEquals(new Point(0, 0), boardGame.getPlayerPosition(player1));
    }

    @Test
    void playerCantMoverAfterReachingZeroLife() {
        GameStatus gameStatus = GameStatus.TESTING;
        Point NOT_IMPORTANT_POINT = new Point(0, 0);
        boardGame.put(player1, NOT_IMPORTANT_POINT);
        player1.damagePlayer(gameStatus);
        player1.damagePlayer(gameStatus);
        player1.damagePlayer(gameStatus);
        Point oldPosition = boardGame.getPlayerPosition(player1);
        boardGame.move(player1, Direction.SOUTH);
        Point newPosition = boardGame.getPlayerPosition(player1);
        assertEquals(oldPosition, newPosition);
    }
}
