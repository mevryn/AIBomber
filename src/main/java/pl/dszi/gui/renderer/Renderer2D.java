package pl.dszi.gui.renderer;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Renderer2D extends Renderer {

    private BufferStrategy bufferStrategy;
    private Graphics graphics;


    @Override
    public void render() {
        bufferStrategy = this.getBufferStrategy();
        if(bufferStrategy == null){
            this.createBufferStrategy(3);
            return;
        }
        graphics = bufferStrategy.getDrawGraphics();
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0,0,960,832);
        graphics.dispose();
        bufferStrategy.show();
    }
    

}
