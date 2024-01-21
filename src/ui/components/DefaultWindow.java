package ui.components;

import javax.swing.*;
import java.awt.*;

public class DefaultWindow extends JFrame {
    private String frameTitle;

    public DefaultWindow(String frameTitle) throws HeadlessException {
        this.frameTitle = frameTitle;

        ImageIcon imageIcon = new ImageIcon("src/ui/components/assets/icon1.png");

//        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setTitle("BCDM");
        this.setSize(520, 420);
        this.setIconImage(imageIcon.getImage());
        this.setLayout(new BorderLayout(1, 1));

        this.getContentPane().setBackground(Color.DARK_GRAY);
    }

    public void showWindow() {
        this.setVisible(true);
    }
}
