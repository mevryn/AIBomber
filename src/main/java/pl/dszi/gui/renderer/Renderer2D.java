package pl.dszi.gui.renderer;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Renderer2D extends Canvas implements Renderer {

    private BufferStrategy bufferStrategy;
    private Graphics graphics;

    @Override
    public void render() {

    }

    public Renderer2D() {
        bufferStrategy = this.getBufferStrategy();
        if(bufferStrategy == null){
            this.createBufferStrategy(3);
            return;
        }
        graphics = bufferStrategy.getDrawGraphics();
    }

}
