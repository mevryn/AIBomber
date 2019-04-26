package pl.dszi.board;

public enum Direction {
    NORTH(0, -5),
    SOUTH(0, 5),
    WEST(-5, 0),
    EAST(5, 0);

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    protected int x;
    protected int y;

}
