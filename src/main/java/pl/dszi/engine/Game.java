package pl.dszi.engine;

import pl.dszi.gui.Window;
import pl.dszi.gui.renderer.Renderer;
import pl.dszi.gui.renderer.Renderer2D;

import java.awt.*;

public class Game implements Runnable{


    private Thread thread;
    private Boolean running = false;

    private Renderer renderer;

    public Game() {
        this.renderer = new Renderer2D();
        new Window(960,832,"AiBomber",renderer);
        this.start();
    }

    @Override
    public void run(){
        long lastTime = System.nanoTime();
        double amountOfTicks=60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running){
            long now = System.nanoTime();
            delta +=(now - lastTime)/ ns;
            lastTime = now;
            while(delta >= 1){
                tick();
                delta --;
            }
            if(running)
                renderer.render();
            frames++;
            if(System.currentTimeMillis()-timer>1000){
                timer += 1000;
                System.out.println("FPS: "+ frames);
                frames = 0;
            }
        }
        stop();
    }
    public synchronized void start(){
        thread = new Thread(this);
        thread.start();
        running=true;
    }
    public synchronized void stop(){
        try{
            thread.join();
            running=false;
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private void render(){

    }

    private void tick(){

    }
    public static void main(final String[] args){
        new Game();
    }
}
