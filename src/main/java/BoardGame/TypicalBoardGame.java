package BoardGame;

import GameEngine.Game;

import java.awt.*;

public class TypicalBoardGame implements BoardGame {
    private int sizeX;
    private int sizeY;
    private int sizeOffsetX;
    private int sizeOffsetY;
    private int tileSize;

    public TypicalBoardGame(int sizeX, int sizeY, int tileSize) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeOffsetX = (Game.WIDTH - sizeX) / 2;
        this.sizeOffsetY = (Game.HEIGHT - sizeY) / 2;
        this.tileSize = tileSize;
    }

    @Override
    public int getSizeX() {
        return sizeX;
    }

    @Override
    public int getSizeY() {
        return sizeY;
    }

    @Override
    public void render(Graphics graphics) {
        boolean even = true;
            graphics.setColor(Color.white);
          graphics.fillRect(sizeOffsetX, sizeOffsetY, sizeX, sizeY);
        for (int i = sizeOffsetX; i < (sizeX+sizeOffsetX); i += tileSize) {
            for (int j = sizeOffsetY; j < (sizeY+sizeOffsetY); j += tileSize) {
                if (even) {
                    graphics.setColor(Color.gray);
                    even = false;
                } else {
                    graphics.setColor(Color.lightGray);
                    even = true;
                }
                  graphics.fillRect(i, j, tileSize, tileSize);

            }
        }
    }
}