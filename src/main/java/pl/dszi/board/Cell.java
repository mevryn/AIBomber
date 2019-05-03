package pl.dszi.board;

import pl.dszi.engine.Constants;
import pl.dszi.player.Player;

import java.awt.*;

public class Cell {

    protected CellType type;
    protected Color color;
    protected final Point point;
    protected   Rectangle body;
    protected int indexX;
    protected int indexY;

    public Point getPoint() {
        return point;
    }

    public CellType getType() {
        return type;
    }

    Cell(CellType type, Point point, boolean even,int indexX, int indexY) {
        this.point = point;
        this.type = type;
        this.color = setColor(even);
        this.indexX=indexX;
        this.indexY=indexY;
        this.body = new Rectangle(point.x, point.y, Constants.DEFAULT_CELL_SIZE, Constants.DEFAULT_CELL_SIZE);
    }

    @Override
    public String toString() {
        return
                "indexX=" + indexX +
                ", indexY=" + indexY+"\n";
    }

    public Rectangle getBody() {
        return body;
    }


    public int getIndexX() {
        return indexX;
    }

    public int getIndexY() {
        return indexY;
    }

    public Cell(CellType type, Point point, int indexX, int indexY) {
        this.point = point;
        this.type = type;
        if(type==CellType.CELL_WALL)
        this.color = setColor();
        this.indexX=indexX;
        this.indexY=indexY;
        this.body = new Rectangle(point.x, point.y, Constants.DEFAULT_CELL_SIZE, Constants.DEFAULT_CELL_SIZE);
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
