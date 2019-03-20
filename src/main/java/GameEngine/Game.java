package GameEngine;

import java.awt.*;
import GUI.Window;

import javax.swing.text.StyledEditorKit;

public class Game extends Canvas implements Runnable{

    public static final int  WIDTH = 640;
    public static final int  HEIGHT = 480;

    private Thread thread;
    private Boolean running = false;

    public Game(){
        Window window = new Window(WIDTH,HEIGHT,"AiBomber",this);
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

    }

    public static void main(String[] args){
        new Game();
    }
}
