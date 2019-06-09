package pl.dszi.board;

import pl.dszi.engine.Game;
import pl.dszi.engine.GameStatus;
import pl.dszi.engine.constant.Constants;

import java.awt.*;

public enum Direction {
    NORTH(0, -Constants.DEFAULT_SPEED),
    EAST(Constants.DEFAULT_SPEED, 0),
    SOUTH(0, Constants.DEFAULT_SPEED),
    WEST(-Constants.DEFAULT_SPEED, 0),
    GENNORTH(0, -Constants.DEFAULT_SPEED*16),
    GENEAST(Constants.DEFAULT_SPEED*16, 0),
    GENSOUTH(0, Constants.DEFAULT_SPEED*16),
    GENWEST(-Constants.DEFAULT_SPEED*16, 0);

    private static Direction[] values  =values();

    Direction(int x, int y) {
            this.x = x;
            this.y = y;
    }



    public int x;
    public int y;

    public Direction turnSide (boolean side){
        if(this==Direction.NORTH && !side){
            return Direction.WEST;
        }else if(this==Direction.WEST && side){
            return Direction.NORTH;
        }
        if(side){
            return values[(ordinal()+1)%values.length];
        }else
            return values[(ordinal()-1)%values.length];
    }

}
