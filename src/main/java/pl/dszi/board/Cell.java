package pl.dszi.board;

import pl.dszi.engine.constant.Constant;

import java.awt.*;

public class Cell {

    private CellType type;
    final Point point;
    private Rectangle body;


    public Point getPoint() {
        return point;
    }

    public CellType getType() {
        return type;
    }

    public Cell(CellType type, Point point) {
        this.point = point;
        this.type = type;
        this.body = new Rectangle(point.x * Constant.DEFAULT_CELL_SIZE, point.y * Constant.DEFAULT_CELL_SIZE, Constant.DEFAULT_CELL_SIZE, Constant.DEFAULT_CELL_SIZE);
    }

    public Rectangle getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "x: " + point.x + "\n" + "y: " + point.y + "\n";
    }

    public void setType(CellType type) {
        this.type = type;
    }
}
