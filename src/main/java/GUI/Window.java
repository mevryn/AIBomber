package GUI;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    private Renderer renderer;
    public Window(int width, int height, String title, Renderer renderer){
            super(title);
         this.setPreferredSize(new Dimension(width,height));
         this.setMinimumSize(new Dimension(width,height));
         this.setMaximumSize(new Dimension(width,height));
         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         this.setResizable(false);
         this.setLocationRelativeTo(null);
         this.setVisible(true);
    }

    public Renderer getRenderer() {
        return renderer;
    }
}
