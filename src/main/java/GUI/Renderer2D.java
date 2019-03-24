package GUI;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Renderer2D extends Canvas implements Renderer {


    public static final int  WIDTH = 640;
    public static final int  HEIGHT = 480;


    public void render(){
        BufferStrategy bufferStrategy = this.getBufferStrategy();
        if(bufferStrategy == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bufferStrategy.getDrawGraphics();
        g.setColor(Color.BLUE);
        g.fillRect(0,0,WIDTH,HEIGHT);
        g.dispose();
        bufferStrategy.show();
    }
}
