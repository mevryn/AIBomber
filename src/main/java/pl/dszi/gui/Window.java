package pl.dszi.gui;

import pl.dszi.engine.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
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
        this.add(renderer);
        BufferedImage img=null;
        try {
             img = ImageIO.read(new URL(
                    "http://www.java2s.com/style/download.png"));
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException i){
            i.printStackTrace();
        }
        ImageIcon imageIcon = new ImageIcon(img);
        JLabel lbl = new JLabel();
        lbl.setIcon(imageIcon);
        this.add(lbl);
        this.setVisible(true);

    }

}
