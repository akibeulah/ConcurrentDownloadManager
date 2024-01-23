package ui.windows;

import state.AppStateManager;
import state.DataChangeListener;
import ui.components.DefaultButton;
import ui.components.DefaultPanel;
import ui.components.DefaultWindow;
import ui.panels.ItemViewerPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainWindow implements DataChangeListener {
    JButton addIcon;
    JButton playIcon;
    JButton viewIcon;
    JButton fileButton;
    public MainWindow() {
        AppStateManager appStateManager = AppStateManager.getInstance();
        DefaultWindow defaultWindow = new DefaultWindow("BCDM");

        addIcon = new DefaultButton("src/ui/components/assets/add.png", "").getjButton();
        playIcon = new DefaultButton("src/ui/components/assets/play.png", "").getjButton();
        viewIcon = new DefaultButton("src/ui/components/assets/view.png", "").getjButton();
        fileButton = new DefaultButton("File").getjButton();
        fileButton.setBorder(null);
        fileButton.setBackground(Color.DARK_GRAY);
        fileButton.setForeground(Color.WHITE);

        addIcon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!appStateManager.isAddNewUrl())
                        new NewDownloadWindow();
                    else
                        appStateManager.setAddNewUrl(false);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // Create panels
        JPanel headerPanel = new DefaultPanel(Color.DARK_GRAY, BoxLayout.Y_AXIS, false);
        JPanel navBarPanel = new DefaultPanel(new FlowLayout(FlowLayout.LEADING, 10, 2), Color.DARK_GRAY, true);
        JPanel gutterPanel = new DefaultPanel(new FlowLayout(FlowLayout.LEADING, 0, 0), Color.DARK_GRAY, true);
        JPanel activityWindowPanel = new DefaultPanel(new BorderLayout(), Color.GREEN, true);
        ItemViewerPanel itemViewerPanel = new ItemViewerPanel(Color.GRAY, BoxLayout.Y_AXIS, false);
        appStateManager.addDataChangeListener(itemViewerPanel);

        addIcon.setBorder(BorderFactory.createEmptyBorder(4, 12, 4, 6));
        addIcon.setBorderPainted(false);
        addIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        playIcon.setBorder(BorderFactory.createEmptyBorder(4, 6, 4, 6));
        playIcon.setBorderPainted(false);
        playIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        playIcon.setEnabled(false);
        viewIcon.setBorder(BorderFactory.createEmptyBorder(4, 6, 4, 6));
        viewIcon.setBorderPainted(false);
        viewIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        viewIcon.setEnabled(false);

        playIcon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                appStateManager.getCDMFile(appStateManager.getSelectedCDMFile()).startDownload();
            }
        });

        gutterPanel.add(addIcon);
        gutterPanel.add(playIcon);
        gutterPanel.add(viewIcon);
        navBarPanel.add(fileButton);

        activityWindowPanel.add(itemViewerPanel, BorderLayout.CENTER);

        defaultWindow.add(headerPanel, BorderLayout.NORTH);
        headerPanel.add(navBarPanel, BorderLayout.NORTH);
        headerPanel.add(gutterPanel, BorderLayout.NORTH);
        defaultWindow.add(activityWindowPanel, BorderLayout.CENTER);

        defaultWindow.showWindow();
    }

    @Override
    public void onDataChanged() {
        playIcon.setEnabled(AppStateManager.getInstance().getSelectedCDMFile() != null);
        viewIcon.setEnabled(AppStateManager.getInstance().getSelectedCDMFile() != null);
    }
}
