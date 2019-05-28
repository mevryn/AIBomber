package pl.dszi.gui;

import pl.dszi.board.BoardGame;
import pl.dszi.engine.constant.Constants;
import pl.dszi.engine.KeyInput;
import pl.dszi.gui.renderer.Renderer;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    public Window(String title, BoardGame boardGame, Renderer renderer) {
        this.add(renderer);
        this.addKeyListener(new KeyInput(boardGame));
        this.setTitle(title);
        this.setSize(new Dimension(Constants.DEFAULT_GAME_WIDTH, Constants.DEFAULT_GAME_HEIGHT));
        this.getContentPane().setPreferredSize(new Dimension(Constants.DEFAULT_GAME_WIDTH, Constants.DEFAULT_GAME_HEIGHT));
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setVisible(true);
    }

}
