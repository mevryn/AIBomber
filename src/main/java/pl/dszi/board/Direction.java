package pl.dszi.board;

import pl.dszi.engine.constant.Constant;

public enum Direction {
    NORTH(0, -Constant.DEFAULT_SPEED),
    EAST(Constant.DEFAULT_SPEED, 0),
    SOUTH(0, Constant.DEFAULT_SPEED),
    WEST(-Constant.DEFAULT_SPEED, 0),
    GENNORTH(0, -Constant.DEFAULT_SPEED * 16),
    GENEAST(Constant.DEFAULT_SPEED * 16, 0),
    GENSOUTH(0, Constant.DEFAULT_SPEED * 16),
    GENWEST(-Constant.DEFAULT_SPEED * 16, 0);

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public int x;
    public int y;

}
