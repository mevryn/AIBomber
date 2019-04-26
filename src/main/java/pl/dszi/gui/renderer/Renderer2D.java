package pl.dszi.gui.renderer;

import pl.dszi.board.Cell;
import pl.dszi.engine.Constants;
import pl.dszi.engine.Game;
import pl.dszi.player.Player;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Map;

public class Renderer2D extends Renderer {

    private BufferStrategy bufferStrategy;
    private Graphics graphics;


    public Renderer2D() {
    }

    @Override
    public void render() {
        bufferStrategy=getBufferStrategy();
        if(bufferStrategy == null){
            this.createBufferStrategy(3);
            return;
        }
        graphics = bufferStrategy.getDrawGraphics();
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0,0,960,832);
    }

    @Override
    public void renderPlayer(Player player,Point point){
        bufferStrategy=getBufferStrategy();
        if(bufferStrategy == null){
            this.createBufferStrategy(3);
            return;
        }
        graphics = bufferStrategy.getDrawGraphics();
        graphics.setColor(player.getColor());
        graphics.fillRect(point.x,point.y,Constants.DEFAULT_CELL_SIZE,Constants.DEFAULT_CELL_SIZE);
    }

    @Override
    public void renderBoardGame(Cell[][] cells){
        bufferStrategy=getBufferStrategy();
        if(bufferStrategy == null){
            this.createBufferStrategy(3);
            return;
        }
        graphics = bufferStrategy.getDrawGraphics();
        for(int i = 0; i<cells.length;i++){
            for(int j = 0; j<cells[i].length;j++) {
                graphics.setColor(cells[i][j].getColor());
                graphics.fillRect(cells[i][j].getPoint().x+Constants.DEFAULT_OFFSET,cells[i][j].getPoint().y+Constants.DEFAULT_OFFSET,Constants.DEFAULT_CELL_SIZE,Constants.DEFAULT_CELL_SIZE);
            }
        }
    }
    @Override
    public void showGraphic() {
        graphics.dispose();
        bufferStrategy.show();
    }

}
