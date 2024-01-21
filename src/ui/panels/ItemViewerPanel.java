package ui.panels;

import state.AppStateManager;
import state.CDMFile;
import state.DataChangeListener;
import ui.components.DefaultPanel;

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
//        itemViewerHeader.setMaximumSize(new Dimension(Integer.MAX_VALUE, itemViewerHeader.getPreferredSize().height));
        this.add(new ItemViewPanel("File Name", "Size", "Status").getContainer());
        for (CDMFile cdmFile : AppStateManager.getInstance().getAllCDMFiles()) {
            this.add(new ItemViewPanel(cdmFile.getName(), String.valueOf(cdmFile.getSize()), String.valueOf(cdmFile.getFileState())).getContainer());
        }

        revalidate();
        repaint();
    }

    @Override
    public void onDataChanged() {
        refreshView();
    }
}
