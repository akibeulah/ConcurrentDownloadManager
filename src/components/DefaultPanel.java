package components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class DefaultPanel extends JPanel {
    public DefaultPanel(Color color, int boxLayoutAxis, boolean border) {
        this.setLayout(new BoxLayout(this, boxLayoutAxis));
        this.setBackground(color);

        if (border) {
            int bottomBorderSize = 1;
            this.setBorder(new MatteBorder(0, 0, bottomBorderSize, 0, Color.BLACK));
        }
    }
    public DefaultPanel(LayoutManager layout, Color color, boolean border) {
        super(layout);
        this.setBackground(color);

        if (border) {
            int bottomBorderSize = 1;
            this.setBorder(new MatteBorder(0, 0, bottomBorderSize, 0, Color.BLACK));
        }
    }
}
