package pl.dszi.gui;

import pl.dszi.engine.Game;
import pl.dszi.gui.renderer.Renderer;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Window extends JFrame {

    public Window(int width, int height, String title,Renderer renderer){
        this.setTitle(title);
        this.setPreferredSize(new Dimension(width,height));
        this.setMinimumSize(new Dimension(width,height));
        this.setMaximumSize(new Dimension(width,height));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.add((Component) renderer);
        BufferedImage img=null;
        this.setVisible(true);

    }

}
