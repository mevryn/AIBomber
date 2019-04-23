package pl.dszi.board;

public enum Direction {
    NORTH(0,-1),
    SOUTH(0,1),
    WEST(-1,0),
    EAST(1,0);

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    protected int x;
    protected int y;

}
