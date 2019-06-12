package pl.dszi.player.noob;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.dszi.board.BoardGame;
import pl.dszi.board.BoardGameGenerator;
import pl.dszi.board.Cell;
import pl.dszi.engine.constant.Constant;
import pl.dszi.player.Player;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NoobPlayerControllerTest {

    private Player player1;
    private Player player2;
    private BoardGameGenerator boardGameGenerator;

    @BeforeEach
    void init(){
        boardGameGenerator= new BoardGameGenerator(new BoardGame(new Cell[Constant.DEFAULT_GAME_TILES_HORIZONTALLY][Constant.DEFAULT_GAME_TILES_VERTICALLY]));
        player1 = new Player(Constant.PLAYER_1_NAME,3);
        player2 = new Player(Constant.PLAYER_2_NAME,3,new NoobPlayerController(boardGameGenerator.getBoardGame()));
        boardGameGenerator.getBoardGame().put(player2,Constant.PLAYER_2_STARTINGLOCATION);
        boardGameGenerator.getBoardGame().put(player1,Constant.PLAYER_1_STARTINGLOCATION);
    }

    @Test
    void playerShouldFindTheWayToEnemy(){
        List<Cell> way = player2.getNoobPlayerController().getAstar().chooseBestWay(boardGameGenerator.getBoardGame().getPlayerPositionCell(player2),boardGameGenerator.getBoardGame().getPlayerPositionCell(player1));
        assertNotEquals(0,way.size());
    }

    @Test
    void playerShouldNotFindTheWayToEnemyOnBoardGameWithGeneratedCrates(){
        boardGameGenerator.initializeCrates();
        List<Cell> way = player2.getNoobPlayerController().getAstar().chooseBestWay(boardGameGenerator.getBoardGame().getPlayerPositionCell(player2),boardGameGenerator.getBoardGame().getPlayerPositionCell(player1));
        assertEquals(0,way.size());
    }

    @Test
    void playerShouldFindClosestCellToEnemyOnGeneratedBoard(){
        boardGameGenerator.initializeCrates();
        List<Cell> way = player2.getNoobPlayerController().getAstar().chooseBestWay(boardGameGenerator.getBoardGame().getPlayerPositionCell(player2),boardGameGenerator.getBoardGame().getPlayerPositionCell(player1));
        assertEquals(0,way.size());
        way = player2.getNoobPlayerController().getAstar().chooseBestWay(boardGameGenerator.getBoardGame().getPlayerPositionCell(player2), player2.getNoobPlayerController().getClosestCellToEnemy(boardGameGenerator.getBoardGame().getPlayerPositionCell(player2.getNoobPlayerController().getClosestPlayer(player2))));
        assertNotEquals(0,way.size());
    }
}