package pl.dszi.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.dszi.engine.constant.Constant;

import static org.junit.jupiter.api.Assertions.*;

class BoardGameInfoTest {

    private BoardGameGenerator boardGameGenerator;

    @BeforeEach
    void init(){
        boardGameGenerator = new BoardGameGenerator(new BoardGame(new Cell[Constant.DEFAULT_GAME_TILES_HORIZONTALLY][Constant.DEFAULT_GAME_TILES_VERTICALLY]));
    }
    @Test
    void cratesAreInitializedProperly() {
        assertEquals(0,boardGameGenerator.getBoardGame().getInfo().getAllSpecificCells(CellType.CELL_CRATE).size());
        boardGameGenerator.initializeCrates();
        assertNotEquals(0,boardGameGenerator.getBoardGame().getInfo().getAllSpecificCells(CellType.CELL_CRATE).size());
    }

    @Test
    void cratesAreDeletedProperly(){
        boardGameGenerator.initializeCrates();
        assertNotEquals(0,boardGameGenerator.getBoardGame().getInfo().getAllSpecificCells(CellType.CELL_CRATE).size());
        boardGameGenerator.getBoardGame().getInfo().setAllCellsToEmpty();
        assertEquals(0,boardGameGenerator.getBoardGame().getInfo().getAllSpecificCells(CellType.CELL_CRATE).size());
    }

}