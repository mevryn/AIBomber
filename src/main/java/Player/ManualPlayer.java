package Player;

import GameEngine.GameObject;

import java.awt.*;

public class ManualPlayer extends GameObject{
    public Color colorOfPlayer;
    public ManualPlayer(int x, int y, ID id) {
        super(x, y, id);
        colorOfPlayer= new Color((int)(Math.random() * 0x1000000));
    }

    @Override
    public void tick() {
        x+=velX;
        y+=velY;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(colorOfPlayer);
        g.fillRect(x,y,32,32);
    }
}
