package pl.dszi.engine;

import pl.dszi.board.BoardGame;
import pl.dszi.gui.Window;
import pl.dszi.gui.renderer.Renderer;
import pl.dszi.gui.renderer.Renderer2D;
import pl.dszi.player.Player;

import java.awt.*;

public class Game implements Runnable{


    private Thread thread;
    private Boolean running = false;

    private Renderer renderer;
    private BoardGame boardGame;

    public BoardGame getBoardGame() {
        return boardGame;
    }

    public Game() {
        this.renderer = new Renderer2D();
        new Window(Constants.DEFAULT_GAME_WIDTH,Constants.DEFAULT_GAME_HEIGHT,"AiBomber",this.renderer);
        this.start();
    }


    public void setBoardGame(BoardGame boardGame){
        this.boardGame = boardGame;
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
            if(running) {
                renderer.renderBoardGame(boardGame.getCells());
                boardGame.getMap().forEach((player, point) -> renderer.renderPlayer(player, point));
                renderer.showGraphic();
            }
            frames++;
            if(System.currentTimeMillis()-timer>1000){
                timer += 1000;
                System.out.println("FPS: "+ frames);
                frames = 0;
            }
        }
        stop();
    }
    void start(){
        thread = new Thread(this);
        thread.start();
        running=true;
    }
    void stop(){
        try{
            running=false;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void tick(){

    }
}
