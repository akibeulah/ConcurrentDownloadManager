package panels;

import components.DefaultPanel;

import javax.swing.*;
import java.awt.*;

public class ItemViewerPanel extends DefaultPanel {
    public ItemViewerPanel(Color color, int boxLayoutAxis, boolean border) {
        super(color, boxLayoutAxis, border);

        JPanel itemViewerHeader = new ItemViewPanel("File Name", "Size", "Status").getContainer();
        this.add(itemViewerHeader);
        itemViewerHeader.setMaximumSize(new Dimension(Integer.MAX_VALUE, itemViewerHeader.getPreferredSize().height));
    }

    public ItemViewerPanel(LayoutManager layout, Color color, boolean border) {
        super(layout, color, border);

        this.add(new ItemViewPanel("File Name", "Size", "Status").getContainer());
    }
}
