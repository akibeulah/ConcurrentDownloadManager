package ui.windows;

import state.AppStateManager;
import state.CDMFile;
import ui.components.DefaultButton;
import ui.components.DefaultPanel;
import ui.components.DefaultWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URISyntaxException;

public class NewDownloadWindow {
    private DefaultWindow defaultWindow;
    private final AppStateManager appStateManager;

    public NewDownloadWindow() throws IOException {
        this.appStateManager = AppStateManager.getInstance();
        this.appStateManager.setAddNewUrl(true);
        defaultWindow = new DefaultWindow("Add URL: ");
        defaultWindow.setAlwaysOnTop(true);
        defaultWindow.setResizable(false);
        defaultWindow.setSize(400, 100);

        JPanel container = new DefaultPanel(Color.DARK_GRAY, BoxLayout.Y_AXIS, false);
        container.setSize(defaultWindow.getWidth(), 50);

        JTextField urlTextField = new JTextField();
        urlTextField.setSize(defaultWindow.getWidth() - 50, 50);
        urlTextField.setBackground(Color.GRAY);
        urlTextField.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));
        container.add(urlTextField);

        JPanel buttonPanel = new DefaultPanel(Color.DARK_GRAY, BoxLayout.X_AXIS, false);

        JButton addButton = new DefaultButton("Add").getjButton();
        JButton cancelButton = new DefaultButton("Cancel").getjButton();

        addButton.setFocusable(false);
        cancelButton.setFocusable(false);
        addButton.setForeground(Color.WHITE);
        cancelButton.setForeground(Color.WHITE);
        addButton.setBackground(new Color(36, 151, 243));
        cancelButton.setBackground(new Color(252, 1, 1));
        addButton.setFocusPainted(false);
        cancelButton.setFocusPainted(false);
        addButton.setBorderPainted(false);
        cancelButton.setBorderPainted(false);
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.setSize(0, 20);
        cancelButton.setSize(0, 20);

        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addButton.setBackground(new Color(31, 122, 204));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                addButton.setBackground(new Color(36, 151, 243));
            }
        });

        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                cancelButton.setBackground(new Color(199, 0, 0));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                cancelButton.setBackground(new Color(252, 1, 1));
            }
        });

        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(addButton);
//        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(cancelButton);
        buttonPanel.add(Box.createHorizontalGlue());

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    appStateManager.addCMDFile(new CDMFile(urlTextField.getText()));
                    ((JFrame) SwingUtilities.getWindowAncestor(addButton)).dispose();
                    appStateManager.setAddNewUrl(false);
                } catch (URISyntaxException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    appStateManager.setAddNewUrl(false);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                ((JFrame) SwingUtilities.getWindowAncestor(addButton)).dispose();
            }
        });

        defaultWindow.add(buttonPanel, BorderLayout.SOUTH);
        defaultWindow.add(container, BorderLayout.CENTER);

        defaultWindow.showWindow();
    }
}
