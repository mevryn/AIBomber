package pl.dszi.gui.renderer;

import pl.dszi.board.Cell;
import pl.dszi.board.CellType;
import pl.dszi.engine.Time;
import pl.dszi.engine.constant.Constant;
import pl.dszi.engine.constant.Resource;
import pl.dszi.player.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

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

    private BufferedImage launcherImg;

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
        graphics.fillRect(0, 0, Constant.DEFAULT_GAME_WIDTH, Constant.DEFAULT_GAME_HEIGHT);
    }

    @Override
    public void renderPlayer(Player player, Point point) {
        bufferStrategy = getBufferStrategy();
        if (characterImg == null) {
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
                graphics.drawImage(characterImg, point.x, point.y, Constant.DEFAULT_CELL_SIZE, Constant.DEFAULT_CELL_SIZE, this);
            }
        } else if (player.isAlive()) {
            graphics.setColor(player.getColor());
            graphics.drawImage(characterImg, point.x, point.y, Constant.DEFAULT_CELL_SIZE, Constant.DEFAULT_CELL_SIZE, this);
        }
    }

    private void blinkImageBool() {
        switcher = !switcher;
    }

    private BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(getClass().getResource(path));
        } catch (IOException ex) {
            throw new RuntimeException("Can't load image", ex);
        }
    }

    private void initializeImg() {

        crateImg = loadImage(Resource.CRATEPATH.getPath());
        launcherImg = loadImage(Resource.LAUNCHERPATH.getPath());
        launcherImg = loadImage(Resource.LAUNCHERPATH.getPath());
        crateBoostImg = loadImage(Resource.CRATEBONUSPATH.getPath());
        boosterImg = loadImage(Resource.BOOSTERPATH.getPath());
        explosionImg = loadImage(Resource.EXPLOSIONPATH.getPath());
        bombImg = loadImage(Resource.BOMBPATH.getPath());
        wallImg = loadImage(Resource.WALLPATH.getPath());

    }

    @Override
    public void renderLauncherWhileLoading() {
        if (renderInit()) return;
        graphics.drawImage(launcherImg, 0, Constant.DEFAULT_GAME_HEIGHT / 4, Constant.DEFAULT_GAME_WIDTH, 416, this);
        graphics.dispose();
        bufferStrategy.show();
    }

    private boolean renderInit() {
        bufferStrategy = getBufferStrategy();
        if (bufferStrategy == null) {
            this.createBufferStrategy(3);
            return true;
        }
        graphics = bufferStrategy.getDrawGraphics();
        graphics.setColor(new Color(34, 139, 34));
        graphics.fillRect(0, 0, Constant.DEFAULT_GAME_WIDTH, Constant.DEFAULT_GAME_HEIGHT);
        return false;
    }

    @Override
    public void renderBoardGame(Cell[][] cells) {
        if (renderInit()) return;
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
                } else if (aCell.getType() == CellType.CELL_CRATEBONUS) {
                    graphics.drawImage(crateBoostImg, aCell.getBody().x, aCell.getBody().y, aCell.getBody().width, aCell.getBody().height, this);
                } else if (aCell.getType() == CellType.CELL_BOOSTER) {
                    graphics.drawImage(boosterImg, aCell.getBody().x, aCell.getBody().y, aCell.getBody().width, aCell.getBody().height, this);
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
