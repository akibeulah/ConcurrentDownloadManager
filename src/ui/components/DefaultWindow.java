package ui.components;

import javax.swing.*;
import java.awt.*;

public class DefaultWindow extends JFrame {

    public DefaultWindow(String frameTitle) throws HeadlessException {

        ImageIcon imageIcon = new ImageIcon("src/ui/components/assets/icon1.png");

//        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(frameTitle);
        this.setSize(520, 420);
        this.setIconImage(imageIcon.getImage());
        this.setLayout(new BorderLayout(1, 1));

        this.getContentPane().setBackground(Color.DARK_GRAY);
    }

    public void showWindow() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - this.getWidth()) / 2;
        int y = (screenSize.height - this.getHeight()) / 2;
        this.setLocation(x, y);
        this.setVisible(true);
    }
}
