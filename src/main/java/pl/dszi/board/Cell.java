package pl.dszi.board;

import java.awt.*;

public class Cell {

    private final CellType type;
    private final Color color;
    private final Point point;

    public Point getPoint() {
        return point;
    }

    public Cell(CellType type, Point point, boolean even) {
        this.type = type;
        this.color = setColor(even);
        this.point = point;

    }
    private Color setColor(boolean even){
        if(even){
            return Color.GRAY;
        }else
            return Color.LIGHT_GRAY;
    }

    public Color getColor() {
        return color;
    }
}
