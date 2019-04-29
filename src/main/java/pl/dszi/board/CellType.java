package pl.dszi.board;

public enum CellType {
    CELL_EMPTY(' ',true),
    CELL_WALL('#',false),
    CELL_CRATE('c',false),
    CELL_BOMB('b',true),
    CELL_BOOM_LEFT('<',false),
    CELL_BOOM_RIGHT('>',false),
    CELL_BOOM_CENTER('+',false),
    CELL_BOOM_VERTICAL('|',false),
    CELL_BOOM_HORIZONTAL('-',false);

    public final char code;

   public boolean walkable;

    private CellType(char code,boolean walkable) {
        this.code = code;
        this.walkable = walkable;
    }
}
