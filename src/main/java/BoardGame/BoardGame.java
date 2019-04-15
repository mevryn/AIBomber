package BoardGame;

import java.awt.*;

public interface BoardGame {
    int getSizeX();
    int getSizeY();
    int getNumberOfTiles(int vertically,int horizontally);
    int getBoardGameTileSize();
    void render(Graphics graphics);
    int countNumberOfObstacles(int size);
}
