package BoardGame;

import BoardGame.Obstacle.SolidObstacle;
import GameEngine.Game;

import java.awt.*;
import java.util.ArrayList;

public class TypicalBoardGame implements BoardGame {
    private int sizeX;
    private int sizeY;
    private int sizeOffsetX;
    private int sizeOffsetY;
    private int tileSize;
    private int gameSizeWidth;
    private int gameSizeHeight;
    private int tilesVertically;
    private int tilesHorizontally;
    private ArrayList<SolidObstacle> solidObstacles = new ArrayList<>();

    @Override
    public int getBoardGameTileSize() {
        return tileSize;
    }

    public TypicalBoardGame(int gameSizeWidth,int gameSizeHeight, int tileSize,int tilesVertically,int tilesHorizontally) {
        this.tileSize = tileSize;
        this.tilesVertically=tilesVertically;
        this.tilesHorizontally = tilesHorizontally;
        this.sizeY = tilesVertically*tileSize;
        this.sizeX = tilesHorizontally*tileSize;
        this.gameSizeWidth = gameSizeWidth;
        this.gameSizeHeight = gameSizeHeight;
        this.sizeOffsetX = (gameSizeWidth - sizeX) / 2;
        this.sizeOffsetY = (gameSizeHeight- sizeY) / 2;
    }


    public int countNumberOfObstacles (int size){
        return size/(tileSize)/2;
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
    public int getNumberOfTiles(int vertically, int horizontally) {
        return 0;
    }

    public void boardGameRender(Graphics graphics){
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
            if(even)
                even = false;
            else
                even = true;
        }
    }
    public void renderSolidObstacles(Graphics graphics){
        boolean even = false;
        for (int i = sizeOffsetX+tileSize; i < (sizeX+sizeOffsetX)-tileSize; i += tileSize*2) {
            for (int j = sizeOffsetY+tileSize; j < (sizeY+sizeOffsetY)-tileSize; j += tileSize) {
                SolidObstacle solidObstacle = new SolidObstacle();
                solidObstacle.render(i,j,graphics);
            }
            if(even)
                even = false;
            else
                even = true;
        }
    }
    @Override
    public void render(Graphics graphics) {
        boardGameRender(graphics);
        renderSolidObstacles(graphics);
    }
}