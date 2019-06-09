package pl.dszi.board;

import pl.dszi.engine.constant.Constants;
import pl.dszi.player.Player;

import java.awt.*;

public class Cell {

    private CellType type;
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
        this.body = new Rectangle(point.x * Constants.DEFAULT_CELL_SIZE, point.y * Constants.DEFAULT_CELL_SIZE, Constants.DEFAULT_CELL_SIZE, Constants.DEFAULT_CELL_SIZE);
    }

    public Rectangle getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "x: "+point.x+"\n"+"y: "+point.y+"\n";
    }

    public void setType(CellType type) {
        this.type = type;
    }
}
