package pl.dszi.gui.renderer;

import pl.dszi.board.Cell;
import pl.dszi.board.CellType;
import pl.dszi.engine.Constants;
import pl.dszi.engine.KeyInput;
import pl.dszi.player.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Renderer2D extends Renderer {

    private BufferStrategy bufferStrategy;
    private Graphics graphics;



    public Renderer2D() {
    }

    @Override
    public void render() {
        bufferStrategy = getBufferStrategy();
        if (bufferStrategy == null) {
            this.createBufferStrategy(3);
            return;
        }
        graphics = bufferStrategy.getDrawGraphics();
        graphics.setColor(Color.DARK_GRAY);
        graphics.fillRect(0, 0, Constants.DEFAULT_GAME_WIDTH, Constants.DEFAULT_GAME_HEIGHT);
    }

    @Override
    public void renderPlayer(Player player, Point point) {
        bufferStrategy = getBufferStrategy();
        BufferedImage img=null;
        try {
            img = ImageIO.read(getClass().getResource("/images/BOMBI.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (bufferStrategy == null) {
            this.createBufferStrategy(3);
            return;
        }
        graphics = bufferStrategy.getDrawGraphics();
        graphics.setColor(player.getColor());
        graphics.drawImage(img,point.x, point.y, Constants.DEFAULT_CELL_SIZE, Constants.DEFAULT_CELL_SIZE,this);
    }

    @Override
    public void renderBoardGame(Cell[][] cells) {
        bufferStrategy = getBufferStrategy();
        if (bufferStrategy == null) {
            this.createBufferStrategy(3);
            return;
        }
        graphics = bufferStrategy.getDrawGraphics();
        for (Cell[] cell : cells) {
            for (Cell aCell : cell) {
                graphics.setColor(aCell.getColor());
                graphics.fillRect(aCell.getPoint().x, aCell.getPoint().y, Constants.DEFAULT_CELL_SIZE, Constants.DEFAULT_CELL_SIZE);
            }
        }
    }

    @Override
    public void renderBomb(Cell[][] cells) {
        BufferedImage img=null;
        try {
            img = ImageIO.read(getClass().getResource("/images/bomba.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        bufferStrategy = getBufferStrategy();
        if (bufferStrategy == null) {
            this.createBufferStrategy(3);
            return;
        }
        graphics = bufferStrategy.getDrawGraphics();
        for (Cell[] cell : cells) {
            for (Cell aCell : cell) {
                if(aCell.getType() == CellType.CELL_BOMB)
                graphics.drawImage(img,aCell.getPoint().x, aCell.getPoint().y, Constants.DEFAULT_CELL_SIZE, Constants.DEFAULT_CELL_SIZE,this);
            }
        }
    }


    @Override
    public void showGraphic() {
        graphics.dispose();
        bufferStrategy.show();
    }

}
