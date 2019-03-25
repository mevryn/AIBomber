package BoardGame.Obstacle;

import java.awt.*;

public class SolidObstacle extends Obstacle{

    public SolidObstacle(int sizeX,int sizeY) {
        this.destroyable = false;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }
    public boolean isDestroyable() {
        return destroyable;
    }

    @Override
    public void render(int x,int y,Graphics graphics) {
        graphics.setColor(Color.black);
        graphics.fillRect(x,y,sizeX,sizeY);
    }
}
