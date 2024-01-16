package windows;

import components.DefaultButton;
import components.DefaultPanel;
import components.DefaultWindow;
import panels.ItemViewerPanel;

import javax.swing.*;
import java.awt.*;

public class MainWindow {
    public MainWindow() {
        DefaultWindow defaultWindow = new DefaultWindow("BCDM");

        JButton addIcon = new DefaultButton("src/Components/assets/add.png", "").getjButton();
        JButton pauseIcon = new DefaultButton("src/Components/assets/pause.png", "").getjButton();
        JButton playIcon = new DefaultButton("src/Components/assets/play.png", "").getjButton();
        JButton fileButton = new DefaultButton("File").getjButton();

        // Create panels
        JPanel headerPanel = new DefaultPanel(Color.DARK_GRAY, BoxLayout.Y_AXIS, false);
        JPanel navBarPanel = new DefaultPanel(new FlowLayout(FlowLayout.LEADING, 10, 2), Color.DARK_GRAY, true);
        JPanel gutterPanel = new DefaultPanel(new FlowLayout(FlowLayout.LEADING, 10, 8), Color.DARK_GRAY, true);
        JPanel activityWindowPanel = new DefaultPanel(new BorderLayout(), Color.GREEN, true);
        JPanel itemViewerPanel = new ItemViewerPanel(Color.GRAY, BoxLayout.Y_AXIS, false);

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
