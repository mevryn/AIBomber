package pl.dszi.gui;

import pl.dszi.gui.renderer.Renderer;

import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Window extends JFrame {

    public Window(int width, int height, String title, Renderer renderer){
        this.setTitle(title);
        this.setPreferredSize(new Dimension(width,height));
        this.setMinimumSize(new Dimension(width,height));
        this.setMaximumSize(new Dimension(width,height));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.add(renderer);
        this.setVisible(true);

    }

}
