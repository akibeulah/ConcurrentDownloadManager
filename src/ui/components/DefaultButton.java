package ui.components;

import javax.swing.*;
import java.awt.*;

public class DefaultButton {
    private final JButton jButton;

    public DefaultButton(String imagePath, String label) {
        ImageIcon imageIcon = new DefaultImageIcon(imagePath, 35, 35).getImageIcon();

        this.jButton = new JButton();
        this.jButton.setIcon(imageIcon);
        this.jButton.setText(label);
        this.jButton.setBackground(Color.DARK_GRAY);
        this.jButton.setFocusable(false);
        this.jButton.setBorder(null);
        this.jButton.setVerticalAlignment(JLabel.CENTER);
    }

    public DefaultButton(String label) {
        this.jButton = new JButton();

        this.jButton.setText(label);
        this.jButton.setText(label);
        this.jButton.setBackground(Color.DARK_GRAY);
        this.jButton.setForeground(Color.WHITE);
        this.jButton.setFocusable(false);
        this.jButton.setBorder(null);
    }

    public JButton getjButton() {
        return jButton;
    }
}
