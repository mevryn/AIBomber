package pl.dszi.gui.renderer;

import pl.dszi.board.BombCell;
import pl.dszi.board.Cell;
import pl.dszi.board.CrateCell;
import pl.dszi.board.ExplosionCell;
import pl.dszi.player.Player;

import java.util.List;

import java.awt.*;
import java.util.Map;

public abstract class Renderer extends Canvas {
    public abstract void render();

    public abstract void renderPlayer(Player player, Point point);

    public abstract void showGraphic();

    public abstract void renderBoardGame(Cell[][] cells);

    public abstract void renderCrates(CrateCell[][] crates);

    public abstract void renderBomb(Map<BombCell,Rectangle> bombCellPointMap);

    public abstract void renderExplosions(List<ExplosionCell> explosionCellList);
}
