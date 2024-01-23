package ui.panels;

import models.CDMFile;
import state.AppStateManager;
import state.DataChangeListener;
import ui.components.DefaultPanel;

import javax.swing.*;
import java.awt.*;

public class ItemViewerPanel extends DefaultPanel implements DataChangeListener {
    public ItemViewerPanel(Color color, int boxLayoutAxis, boolean border) {
        super(color, boxLayoutAxis, border);
        refreshView();
    }

    public ItemViewerPanel(LayoutManager layout, Color color, boolean border) {
        super(layout, color, border);
        refreshView();
    }

    private void refreshView() {
        this.removeAll();
        JPanel itemViewScrollPanel = new DefaultPanel(Color.GRAY, BoxLayout.Y_AXIS, false);
        JScrollPane scrollPane = new JScrollPane(itemViewScrollPanel);
        scrollPane.setBorder(null);

        this.add(new ItemViewPanel("File Name", "Size", "Status").getContainer());

        for (CDMFile cdmFile : AppStateManager.getInstance().getAllCDMFiles()) {
            ItemViewPanel itemViewPanel = new ItemViewPanel(cdmFile);
            itemViewScrollPanel.add(itemViewPanel.getContainer());
        }
        this.add(scrollPane);

        revalidate();
        repaint();
    }

    @Override
    public void onDataChanged() {
        refreshView();
    }
}
