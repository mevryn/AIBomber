package pl.dszi.engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.dszi.board.BoardGame;
import pl.dszi.board.Cell;
import pl.dszi.board.CellType;
import pl.dszi.engine.constant.Constant;
import pl.dszi.player.Player;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;

class BattleEngineTest {
    private Player player1;
    private Player player2;
    private BoardGame boardGame;
    @BeforeEach
    void init(){
        String NOT_IMPORTNANT_NAME="player";
        int hp = 3;
        boardGame = new BoardGame(new Cell[Constant.DEFAULT_GAME_TILES_HORIZONTALLY][Constant.DEFAULT_GAME_TILES_VERTICALLY]);
        player1 = new Player(NOT_IMPORTNANT_NAME,hp);
    }


    @Test
    void playerCanPlantBomb(){
        boardGame.put(player1,new Point(0,0));
        boardGame.plantBomb(player1);
        CellType afterPlant = boardGame.getPlayerPositionCell(player1).getType();
        assertEquals(CellType.CELL_BOMB,afterPlant);
    }

    @Test
    void playerTakeDamageFromExplosion() {
        boardGame.put(player1,new Point(0,0));
        boardGame.getCells()[0][0].setType(CellType.CELL_BOOM_CENTER);
        boardGame.damageAllPlayersIntersectingWithExplosion();
        assertEquals(2,player1.getCurrentHp());
    }
    

}
