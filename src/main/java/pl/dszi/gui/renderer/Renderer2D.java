package pl.dszi.gui.renderer;

import pl.dszi.board.Cell;
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
        graphics.setColor(Color.BLACK);
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
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                graphics.setColor(cells[i][j].getColor());
                graphics.fillRect(cells[i][j].getPoint().x + Constants.DEFAULT_OFFSET, cells[i][j].getPoint().y + Constants.DEFAULT_OFFSET, Constants.DEFAULT_CELL_SIZE, Constants.DEFAULT_CELL_SIZE);
            }
        }
    }

    @Override
    public void showGraphic() {
        graphics.dispose();
        bufferStrategy.show();
    }

}
