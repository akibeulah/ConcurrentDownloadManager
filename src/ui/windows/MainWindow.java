package ui.windows;

import state.AppStateManager;
import ui.components.DefaultButton;
import ui.components.DefaultPanel;
import ui.components.DefaultWindow;
import ui.panels.ItemViewerPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainWindow {
    public MainWindow() {
        AppStateManager appStateManager = AppStateManager.getInstance();
        DefaultWindow defaultWindow = new DefaultWindow("BCDM");

        JButton addIcon = new DefaultButton("src/ui/components/assets/add.png", "").getjButton();
        JButton pauseIcon = new DefaultButton("src/ui/components/assets/pause.png", "").getjButton();
        JButton playIcon = new DefaultButton("src/ui/components/assets/play.png", "").getjButton();
        JButton fileButton = new DefaultButton("File").getjButton();

        addIcon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!appStateManager.isAddNewUrl()) {
                    try {
                        new NewDownloadWindow();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        // Create panels
        JPanel headerPanel = new DefaultPanel(Color.DARK_GRAY, BoxLayout.Y_AXIS, false);
        JPanel navBarPanel = new DefaultPanel(new FlowLayout(FlowLayout.LEADING, 10, 2), Color.DARK_GRAY, true);
        JPanel gutterPanel = new DefaultPanel(new FlowLayout(FlowLayout.LEADING, 10, 8), Color.DARK_GRAY, true);
        JPanel activityWindowPanel = new DefaultPanel(new BorderLayout(), Color.GREEN, true);
        ItemViewerPanel itemViewerPanel = new ItemViewerPanel(Color.GRAY, BoxLayout.Y_AXIS, false);
        appStateManager.addDataChangeListener(itemViewerPanel);

        gutterPanel.add(addIcon);
        gutterPanel.add(playIcon);
        navBarPanel.add(fileButton);

        activityWindowPanel.add(itemViewerPanel, BorderLayout.CENTER);

        // Add panels to DefaultWindow
        defaultWindow.add(headerPanel, BorderLayout.NORTH);
        headerPanel.add(navBarPanel, BorderLayout.NORTH);
        headerPanel.add(gutterPanel, BorderLayout.NORTH);
        defaultWindow.add(activityWindowPanel, BorderLayout.CENTER);

        // Make the window visible
        defaultWindow.showWindow();
    }
}
