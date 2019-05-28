package pl.dszi.board;

import pl.dszi.engine.constant.Constants;
import pl.dszi.player.Player;

import java.awt.*;

public class Cell {

    private CellType type;
    private Color color;
    final Point point;
    private Rectangle body;
    private Player player;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Point getPoint() {
        return point;
    }

    public CellType getType() {
        return type;
    }

    Cell(CellType type, Point point) {
        this.point = point;
        this.type = type;
        this.player = Constants.godPlayer;
        if (type == CellType.CELL_WALL) {
            color = Color.BLACK;
        } else
            color = Color.LIGHT_GRAY;
        this.body = new Rectangle(point.x * Constants.DEFAULT_CELL_SIZE, point.y * Constants.DEFAULT_CELL_SIZE, Constants.DEFAULT_CELL_SIZE, Constants.DEFAULT_CELL_SIZE);
    }


    public Rectangle getBody() {
        return body;
    }


    void setType(CellType type) {
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
