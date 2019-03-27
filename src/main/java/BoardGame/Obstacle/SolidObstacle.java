package BoardGame.Obstacle;

import java.awt.*;

public class SolidObstacle extends Obstacle{

    public SolidObstacle() {
        this.destroyable = false;
        this.sizeX = 32;
        this.sizeY = 32;
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
