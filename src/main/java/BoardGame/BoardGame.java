package BoardGame;

import java.awt.*;

public interface BoardGame {
    int getSizeX();
    int getSizeY();
    int getBoardGameTileSize();
    void render(Graphics graphics);
    int countNumberOfObstacles(int size);
}
