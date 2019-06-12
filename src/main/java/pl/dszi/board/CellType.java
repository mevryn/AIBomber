package pl.dszi.board;

import pl.dszi.player.Player;

public enum CellType {
    CELL_EMPTY(' ', true),
    CELL_WALL('#', false),
    CELL_CRATE('c', false),
    CELL_CRATEBONUS('t', false),
    CELL_BOOSTER('o', true),
    CELL_BOMB('b', true),
    CELL_BOOM_LEFT('<', false),
    CELL_BOOM_RIGHT('>', false),
    CELL_BOOM_CENTER('+', true),
    CELL_BOOM_VERTICAL('|', false),
    CELL_BOOM_HORIZONTAL('-', false);

    public final char code;

    public boolean destroyable;

    public boolean walkable;


    CellType(char code, boolean walkable) {
        this.code = code;
        this.walkable = walkable;
        destroyable = code == 'c';
    }

}
