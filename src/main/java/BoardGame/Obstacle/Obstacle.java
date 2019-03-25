package BoardGame.Obstacle;

import java.awt.*;

public abstract class Obstacle {
    protected int sizeX;
    protected int sizeY;
    protected boolean destroyable;

    public abstract void render(int x,int y,Graphics graphics);
}
