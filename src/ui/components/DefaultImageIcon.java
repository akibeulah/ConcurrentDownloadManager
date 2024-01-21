package ui.components;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class DefaultImageIcon {
    private final ImageIcon imageIcon;

    public DefaultImageIcon(String imagePath, int width, int height) {
        if (!(new File(imagePath).exists()))
            System.out.printf("%s does not exist\n", imagePath);

        this.imageIcon = resizeIcon(new ImageIcon(imagePath), width, height);
    }

    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image image = icon.getImage();
        Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    public ImageIcon getImageIcon() {
        return imageIcon;
    }
}
