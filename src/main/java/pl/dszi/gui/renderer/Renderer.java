package pl.dszi.gui.renderer;

import pl.dszi.board.Cell;
import pl.dszi.player.Player;

import java.awt.*;

public abstract class Renderer extends Canvas {
    public abstract void render();

    public abstract void renderPlayer(Player player, Point point);

    public abstract void showGraphic();

    public abstract void renderBoardGame(Cell[][] cells);
}
