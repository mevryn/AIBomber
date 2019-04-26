package pl.dszi.board;

public enum CellType {
    CELL_EMPTY(' '),
    CELL_WALL('#'),
    CELL_CRATE('c'),
    CELL_BOMB('b'),
    CELL_BOOM_LEFT('<'),
    CELL_BOOM_RIGHT('>'),
    CELL_BOOM_CENTER('+'),
    CELL_BOOM_VERTICAL('|'),
    CELL_BOOM_HORIZONTAL('-');

    public final char code;
    private CellType(char code){
        this.code = code;
    }
}
