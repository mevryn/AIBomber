package pl.dszi.board;

import pl.dszi.engine.constant.Constants;

import java.awt.*;

public class Cell {

    protected CellType type;
    protected Color color;
    protected final Point point;
    private   Rectangle body;
    public Point getPoint() {
        return point;
    }

    public CellType getType() {
        return type;
    }

    Cell(CellType type, Point point) {
        this.point = point;
        this.type = type;
        if(type == CellType.CELL_WALL){
            color = Color.BLACK;
        }else
            color = Color.LIGHT_GRAY;
        this.body = new Rectangle(point.x*Constants.DEFAULT_CELL_SIZE, point.y*Constants.DEFAULT_CELL_SIZE, Constants.DEFAULT_CELL_SIZE, Constants.DEFAULT_CELL_SIZE);
    }


    public Rectangle getBody() {
        return body;
    }


    public void setType(CellType type) {
        this.type = type;
    }

    private Color setColor() {
        return Color.BLACK;
    }


    private Color setColor(boolean even) {
        if (even) {
            return Color.GRAY;
        } else
            return Color.LIGHT_GRAY;
    }

    public Color getColor() {
        return color;
    }

}
