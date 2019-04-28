package pl.dszi.board;

import pl.dszi.engine.Constants;

public enum Direction {
    NORTH(0, -Constants.DEFAULT_SPEED),
    SOUTH(0, Constants.DEFAULT_SPEED),
    WEST(-Constants.DEFAULT_SPEED, 0),
    EAST(Constants.DEFAULT_SPEED, 0);

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    protected int x;
    protected int y;

}
