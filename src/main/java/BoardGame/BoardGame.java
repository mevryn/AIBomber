package BoardGame;

import java.awt.*;

public interface BoardGame {
    int getSizeX();
    int getSizeY();
    void render(Graphics graphics);
}
