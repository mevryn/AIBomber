package BoardGame;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TypicalBoardGameTest {


    BoardGame typicalBoardGame = new TypicalBoardGame(800,640,608,448,32);
    @Test
    void countNumberOfObstacles() {
        assertEquals(9,typicalBoardGame.countNumberOfObstacles(typicalBoardGame.getSizeX()));
        assertEquals(7,typicalBoardGame.countNumberOfObstacles(typicalBoardGame.getSizeY()));
    }
}