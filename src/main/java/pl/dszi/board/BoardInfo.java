package pl.dszi.board;

import pl.dszi.engine.Constants;

import java.awt.*;

public class BoardInfo {

    public final Dimension gridSize;

    public final int cellSize;

    public final Dimension pixelSize;

    BoardInfo(BoardGame boardGame) {
        this(new Dimension(boardGame.width, boardGame.height), Constants.DEFAULT_CELL_SIZE);
        {

        }
    }

    public BoardInfo(Dimension gridSize, int defaultCellSize) {
        this.gridSize = gridSize;
        this.cellSize = defaultCellSize;
        this.pixelSize = new Dimension(
                gridSize.width * cellSize,
                gridSize.height * cellSize);
    }

    public Point pixelToGrid(Point location) {
        return new Point(
                location.x / cellSize,
                location.y / cellSize);
    }

    public Point gridToPixel(Point location) {
        return new Point(
                location.x * cellSize + cellSize / 2,
                location.y * cellSize + cellSize / 2);
    }


}
