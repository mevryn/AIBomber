package pl.dszi.gui;

import pl.dszi.board.BoardGame;
import pl.dszi.engine.Constants;
import pl.dszi.engine.KeyInput;
import pl.dszi.gui.renderer.Renderer;
import pl.dszi.player.ManualPlayerController;
import pl.dszi.player.PlayerController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Window extends JFrame {

    public Window(int width, int height, String title,BoardGame boardGame, Renderer renderer) {
        this.add(renderer);
        this.addKeyListener(new KeyInput(boardGame));
        this.setTitle(title);
        this.setSize(new Dimension(Constants.DEFAULT_GAME_WIDTH+Constants.HALF_DEFAULT_CELL_SIZE_RENDER,Constants.DEFAULT_GAME_HEIGHT+Constants.HALF_DEFAULT_CELL_SIZE));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        this.setVisible(true);
    }

}
