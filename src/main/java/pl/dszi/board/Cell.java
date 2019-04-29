package pl.dszi.board;

import pl.dszi.engine.Constants;

import java.awt.*;

public class Cell {

    private final CellType type;
    private final Color color;
    private final Point point;
    private final Rectangle body;
    public Point getPoint() {
        return point;
    }

    CellType getType() {
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

    boolean checkIfPointIsInsideCell(Point aPoint){
            return aPoint.x > this.body.x &&
                    aPoint.y > this.body.y &&
                    aPoint.x < this.body.x+this.body.width &&
                    aPoint.y < this.body.x+this.body.height;
        }
    Cell(CellType type, Point point){
        this.point = point;
        this.type = type;
        this.color = setColor();
        this.body = new Rectangle(point.x,point.y,Constants.DEFAULT_CELL_SIZE,Constants.DEFAULT_CELL_SIZE);
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
