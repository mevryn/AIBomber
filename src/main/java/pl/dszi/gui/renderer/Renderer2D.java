package pl.dszi.gui.renderer;

import pl.dszi.board.*;
import pl.dszi.engine.constant.Constants;
import pl.dszi.engine.constant.Resource;
import pl.dszi.player.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

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
            img = ImageIO.read(getClass().getResource(Resource.CHARACTERPATH.getPath()));
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
        final BufferedImage crateImg;
        final BufferedImage explosionImg;
        final BufferedImage bombImg;
        BufferedImage temp;
        try {
            temp = ImageIO.read(getClass().getResource(Resource.CRATEPATH.getPath()));
        } catch (IOException ex) {
            ex.printStackTrace();
            temp = null;
        }
        crateImg = temp;
        try {
            temp = ImageIO.read(getClass().getResource(Resource.EXPLOSIONPATH.getPath()));
        } catch (IOException ex) {
            ex.printStackTrace();
            temp = null;
        }
        explosionImg = temp;
        try {
            temp = ImageIO.read(getClass().getResource(Resource.BOMBPATH.getPath()));
        } catch (IOException ex) {
            ex.printStackTrace();
            temp = null;
        }
        bombImg = temp;
        graphics = bufferStrategy.getDrawGraphics();
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(0, 0, Constants.DEFAULT_GAME_WIDTH, Constants.DEFAULT_GAME_HEIGHT);
        for (Cell[] cell : cells) {
            for (Cell aCell : cell) {
                graphics.setColor(Color.LIGHT_GRAY);
                if (aCell.getType() == CellType.CELL_WALL) {
                    graphics.setColor(Color.BLACK);
                    graphics.fillRect(aCell.getBody().x, aCell.getBody().y, Constants.DEFAULT_CELL_SIZE, Constants.DEFAULT_CELL_SIZE);
                }else if (aCell.getType() == CellType.CELL_CRATE) {
                    graphics.drawImage(crateImg, aCell.getBody().x, aCell.getBody().y, aCell.getBody().width, aCell.getBody().height, this);
                }else if (aCell.getType() == CellType.CELL_BOOM_CENTER) {
                    graphics.drawImage(explosionImg, aCell.getBody().x, aCell.getBody().y, aCell.getBody().width, aCell.getBody().height, this);
                }else if (aCell.getType() == CellType.CELL_BOMB) {
                    graphics.drawImage(bombImg, aCell.getBody().x, aCell.getBody().y, aCell.getBody().width, aCell.getBody().height, this);
                }
            }
        }
    }


    @Override
    public void showGraphic() {
        graphics.dispose();
        bufferStrategy.show();
    }

}
