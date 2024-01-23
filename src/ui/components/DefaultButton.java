package ui.components;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
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

        this.jButton.setUI(new CustomButtonUI());
    }

    public DefaultButton(String label) {
        this.jButton = new JButton();

        this.jButton.setText(label);
        this.jButton.setFocusable(false);

        this.jButton.setUI(new CustomButtonUI());
    }

    public JButton getjButton() {
        return jButton;
    }

    private static class CustomButtonUI extends BasicButtonUI {
        @Override
        protected void paintButtonPressed(Graphics g, AbstractButton b) {
            g.setColor(null);
            g.fillRect(0, 0, b.getWidth(), b.getHeight());
            super.paintButtonPressed(g, b);
        }
    }
}
