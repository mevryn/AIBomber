package GameEngine;

import BoardGame.TypicalBoardGame;
import GUI.Renderer;
import GUI.Renderer2D;
import GUI.Window;
import Player.ID;
import Player.ManualPlayer;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable{

    public static final int  WIDTH = 800;
    public static final int  HEIGHT = 600;

    private Handler handler;
    private Thread thread;
    private Boolean running = false;
    public Game(){
        new Window(WIDTH,HEIGHT,"AiBomber",this);
        handler = new Handler();
        handler.setBoardGame(new TypicalBoardGame(640,480,32));
        handler.addObject(new ManualPlayer(50,50, ID.Player));
        handler.addObject(new ManualPlayer(100,100, ID.Player));
        handler.addObject(new ManualPlayer(150,150, ID.MyAI));
        handler.addObject(new ManualPlayer(200,200, ID.MyAI));
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
              render();
            frames++;
            if(System.currentTimeMillis()-timer>1000){
                timer += 1000;
                System.out.println("FPS: "+ frames);
                frames = 0;
            }
        }
        stop();
    }

    public void render(){
        BufferStrategy bufferStrategy = this.getBufferStrategy();
        if(bufferStrategy == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bufferStrategy.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0,0,WIDTH,HEIGHT);
        handler.render(g);
        g.dispose();
        bufferStrategy.show();
    }
    public void tick(){
        handler.tick();
    }

    public static void main(String[] args){
        new Game();
    }
}
