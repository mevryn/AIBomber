package GameEngine;

import GUI.Renderer;
import GUI.Renderer2D;
import GUI.Window;

public class Game implements Runnable{

    public static final int  WIDTH = 640;
    public static final int  HEIGHT = 480;

    private Thread thread;
    private Boolean running = false;
    private Window window;
    public Game(){
        window = new Window(WIDTH,HEIGHT,"AiBomber",new Renderer2D());
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

    @Override
    public void run() {
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
                window.getRenderer().render();
            frames++;
            if(System.currentTimeMillis()-timer>1000){
                timer += 1000;
                System.out.println("FPS: "+ frames);
                frames = 0;
            }
        }
        stop();
    }

    public void tick(){

    }

    public static void main(String[] args){
        new Game();
    }
}
