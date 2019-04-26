package pl.dszi.board;

import java.awt.*;

public class Cell {

    private final CellType type;
    private final Color color;
    private final Point point;

    public Point getPoint() {
        return point;
    }

    public CellType getType() {
        return type;
    }

    public Cell(CellType type, Point point, boolean even) {
        this.type = type;
        this.color = setColor(even);
        this.point = point;

    }

    public Cell(CellType type,Point point){
        this.type = type;
        this.point = point;
        this.color = setColor();
    }

    private Color setColor(){
        return Color.BLACK;
    }
    private Color setColor(boolean even) {
        if (even) {
            return Color.GRAY;
        }
        else
            return Color.LIGHT_GRAY;
    }

    public Color getColor() {
        return color;
    }
}
