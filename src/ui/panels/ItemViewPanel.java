package ui.panels;

import models.CDMFile;
import state.AppStateManager;
import ui.components.DefaultButton;
import ui.components.DefaultPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Objects;

public class ItemViewPanel {
    private CDMFile cdmFile;
    private final JPanel container;
    private final JLabel fileNameLabel;
    private final JLabel sizeLabel;
    private final JLabel statusLabel;
    JButton hackButton;

    // Constructor for the case without CDMFile
    public ItemViewPanel(String fileName, String size, String status) {
        container = new DefaultPanel(Color.DARK_GRAY, BoxLayout.X_AXIS, true);

        hackButton = createStyledButton();
        hackButton.setEnabled(false);

        fileNameLabel = createLabel(fileName);
        sizeLabel = createLabel(size);
        statusLabel = createLabel(status);

        hackButton.add(fileNameLabel);
        hackButton.add(Box.createHorizontalGlue());
        hackButton.add(Box.createRigidArea(new Dimension(10, 0)));
        hackButton.add(sizeLabel);
        hackButton.add(Box.createRigidArea(new Dimension(10, 0)));
        hackButton.add(statusLabel);

        container.add(hackButton);
    }

    // Constructor for the case with CDMFile
    public ItemViewPanel(CDMFile cdmFile) {
        this.cdmFile = cdmFile;
        container = new DefaultPanel(Color.DARK_GRAY, BoxLayout.X_AXIS, false);

        hackButton = createStyledButton();
        configureButtonListeners(hackButton);

        fileNameLabel = createLabel(cdmFile.getName());
        sizeLabel = createLabel(String.valueOf(cdmFile.getSize()));
        statusLabel = createLabel(String.valueOf(cdmFile.getFileState()));

        hackButton.add(fileNameLabel);
        hackButton.add(Box.createHorizontalGlue());
        hackButton.add(Box.createRigidArea(new Dimension(10, 0)));
        hackButton.add(sizeLabel);
        hackButton.add(Box.createRigidArea(new Dimension(10, 0)));
        hackButton.add(statusLabel);

        container.add(hackButton);
    }

    public JPanel getContainer() {
        return container;
    }

    private JButton createStyledButton() {
        JButton hackButton = new DefaultButton("").getjButton();
        hackButton.setBackground(Color.DARK_GRAY);
        hackButton.setFocusable(false);
        hackButton.setLayout(new BoxLayout(hackButton, BoxLayout.X_AXIS));
        hackButton.setFocusPainted(false);
        hackButton.setBorderPainted(false);
        hackButton.setSize(container.getSize());
        hackButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return hackButton;
    }

    private void configureButtonListeners(JButton hackButton) {
        hackButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hackButton.setBackground(new Color(54, 54, 54));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!Objects.equals(AppStateManager.getInstance().getSelectedCDMFile(), cdmFile.getID()))
                    hackButton.setBackground(Color.DARK_GRAY);
            }
        });

        hackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (Objects.equals(AppStateManager.getInstance().getSelectedCDMFile(), cdmFile.getID()))
                        AppStateManager.getInstance().setSelectedCDMFile(null);
                    else
                        AppStateManager.getInstance().setSelectedCDMFile(cdmFile.getID());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel();
        label.setText(text);
        label.setForeground(Color.WHITE);
        return label;
    }
}
