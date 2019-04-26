package pl.dszi.gui;

import pl.dszi.board.BoardGame;
import pl.dszi.engine.KeyInput;
import pl.dszi.gui.renderer.Renderer;
import pl.dszi.player.ManualPlayerController;
import pl.dszi.player.PlayerController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Window extends JFrame {

    BoardGame boardGame;

    public Window(int width, int height, String title,BoardGame boardGame, Renderer renderer) {
        this.boardGame=boardGame;
        this.add(renderer);
        this.addKeyListener(new KeyInput(boardGame));
        this.setTitle(title);
        this.setPreferredSize(new Dimension(width, height));
        this.setMinimumSize(new Dimension(width, height));
        this.setMaximumSize(new Dimension(width, height));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        this.setVisible(true);
    }

}
