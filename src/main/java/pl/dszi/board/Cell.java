package pl.dszi.board;

import pl.dszi.engine.Constants;

import java.awt.*;

public class Cell {

    private CellType type;
    private final Color color;
    private final Point point;
    private final Rectangle body;
    public Point getPoint() {
        return point;
    }

    public CellType getType() {
        return type;
    }

     Cell(CellType type, Point point, boolean even) {
        this.point = point;
        this.type = type;
        this.color = setColor(even);
        this.body = new Rectangle(point.x,point.y,Constants.DEFAULT_CELL_SIZE,Constants.DEFAULT_CELL_SIZE);
    }

    public Rectangle getBody() {
        return body;
    }

    Cell(CellType type, Point point){
        this.point = point;
        this.type = type;
        this.color = setColor();
        this.body = new Rectangle(point.x,point.y,Constants.DEFAULT_CELL_SIZE,Constants.DEFAULT_CELL_SIZE);
    }


    public void setType(CellType type) {
        this.type = type;
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
