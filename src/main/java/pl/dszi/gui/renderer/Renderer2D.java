package pl.dszi.gui.renderer;

import pl.dszi.board.Cell;
import pl.dszi.board.CellType;
import pl.dszi.engine.Time;
import pl.dszi.engine.constant.Constants;
import pl.dszi.engine.constant.Resource;
import pl.dszi.player.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Renderer2D extends Renderer {

    private BufferStrategy bufferStrategy;
    private Graphics graphics;

    private BufferedImage crateImg;
    private BufferedImage characterImg;
    private BufferedImage crateBoostImg;
    private BufferedImage boosterImg;
    private BufferedImage explosionImg;
    private BufferedImage bombImg;
    private BufferedImage wallImg;

    private boolean switcher = true;

    public Renderer2D() {
        initializeImg();
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
        if(characterImg == null) {
            try {
                characterImg = ImageIO.read(getClass().getResource(Resource.CHARACTERPATH.getPath()));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (bufferStrategy == null) {
            this.createBufferStrategy(3);
            return;
        }
        graphics = bufferStrategy.getDrawGraphics();
        if (!player.isMortal() && player.isAlive()) {
            Time.scheduleTimer(this::blinkImageBool, 0);
            if (switcher) {
                graphics.drawImage(characterImg, point.x, point.y, Constants.DEFAULT_CELL_SIZE, Constants.DEFAULT_CELL_SIZE, this);
            }
        } else if (player.isAlive()) {
            graphics.setColor(player.getColor());
            graphics.drawImage(characterImg, point.x, point.y, Constants.DEFAULT_CELL_SIZE, Constants.DEFAULT_CELL_SIZE, this);
        }
    }

    private void blinkImageBool() {
        switcher = !switcher;
    }
    private void initializeImg() {
        if(crateImg == null) {
            try {
                crateImg = ImageIO.read(getClass().getResource(Resource.CRATEPATH.getPath()));
            } catch (IOException ex) {
                ex.printStackTrace();
                crateImg = null;
            }
        }
        if(explosionImg == null) {
            try {
                explosionImg = ImageIO.read(getClass().getResource(Resource.EXPLOSIONPATH.getPath()));
            } catch (IOException ex) {
                ex.printStackTrace();
                explosionImg = null;
            }
        }
        if(bombImg == null) {
            try {
                bombImg = ImageIO.read(getClass().getResource(Resource.BOMBPATH.getPath()));
            } catch (IOException ex) {
                ex.printStackTrace();
                bombImg = null;
            }
        }
        if(wallImg == null) {
            try {
                wallImg = ImageIO.read(getClass().getResource(Resource.WALLPATH.getPath()));
            } catch (IOException ex) {
                ex.printStackTrace();
                wallImg = null;
            }
        }
    }

    @Override
    public void renderBoardGame(Cell[][] cells) {
        bufferStrategy = getBufferStrategy();
        if (bufferStrategy == null) {
            this.createBufferStrategy(3);
            return;
        }
        graphics = bufferStrategy.getDrawGraphics();
        graphics.setColor(new Color(34, 139, 34));
        graphics.fillRect(0, 0, Constants.DEFAULT_GAME_WIDTH, Constants.DEFAULT_GAME_HEIGHT);
        for (Cell[] columns : cells) {
            for (Cell aCell : columns) {
                graphics.setColor(Color.LIGHT_GRAY);
                if (aCell.getType() == CellType.CELL_WALL) {
                    graphics.drawImage(wallImg, aCell.getBody().x, aCell.getBody().y, aCell.getBody().width, aCell.getBody().height, this);
                } else if (aCell.getType() == CellType.CELL_CRATE) {
                    graphics.drawImage(crateImg, aCell.getBody().x, aCell.getBody().y, aCell.getBody().width, aCell.getBody().height, this);
                } else if (aCell.getType() == CellType.CELL_BOOM_CENTER) {
                    graphics.drawImage(explosionImg, aCell.getBody().x, aCell.getBody().y, aCell.getBody().width, aCell.getBody().height, this);
                } else if (aCell.getType() == CellType.CELL_BOMB) {
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
