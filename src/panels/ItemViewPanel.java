package panels;

import components.DefaultPanel;
import enums.DownloadStatus;

import javax.swing.*;
import java.awt.*;

public class ItemViewPanel {
    private JPanel container;
    private JLabel fileNameLabel;
    private JLabel sizeLabel;
    private JLabel statusLabel;
    private DownloadStatus status = DownloadStatus.STARTING;
    private String fileName;
    private String size;
    public ItemViewPanel(String fileName, String size, String status) {
        container = new DefaultPanel(Color.DARK_GRAY, BoxLayout.X_AXIS, true);

        fileNameLabel = new JLabel();
        fileNameLabel.setText(fileName);
        fileNameLabel.setForeground(Color.WHITE);
        sizeLabel = new JLabel();
        sizeLabel.setText(size);
        sizeLabel.setForeground(Color.WHITE);
        statusLabel = new JLabel();
        statusLabel.setText(status);
        statusLabel.setForeground(Color.WHITE);

        container.add(Box.createRigidArea(new Dimension(10, 0)));
        container.add(fileNameLabel);
        container.add(Box.createHorizontalGlue());

        container.add(Box.createRigidArea(new Dimension(10, 0)));
        container.add(sizeLabel);

        container.add(Box.createRigidArea(new Dimension(10, 0)));
        container.add(statusLabel);
        container.add(Box.createRigidArea(new Dimension(10, 0)));
    }

    public JPanel getContainer() {
        return container;
    }

    public void updateItemData(String fileName, String size, DownloadStatus status) {
        if (!fileName.isEmpty())
            setFileName(fileName);

        if (!size.isEmpty())
            setSize(size);

        if (status != DownloadStatus.DEAD)
            setStatus(DownloadStatus.valueOf(status.toString()));
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setStatus(DownloadStatus status) {
        this.status = status;
    }
}
