package ui.components;

import javax.swing.*;
import java.awt.*;

public class PopUpWindow extends JFrame {
    final static String logoText = "oooooooooo.        .oooooo.       oooooooooo.       ooo        ooooo\n" +
            "`888'   `Y8b      d8P'  `Y8b      `888'   `Y8b      `88.       .888'\n" +
            " 888     888     888               888      888      888b     d'888\n" +
            " 888oooo888'     888               888      888      8 Y88. .P  888\n" +
            " 888    `88b     888               888      888      8  `888'   888\n" +
            " 888    .88P     `88b    ooo       888     d88'      8    Y     888\n" +
            "o888bood8P'       `Y8bood8P'      o888bood8P'       o8o        o888o";
    private String frameTitle;

    public PopUpWindow(String frameTitle) throws HeadlessException {
        this.frameTitle = frameTitle;


        ImageIcon imageIcon = new ImageIcon("src/JFrames/assets/icon1.png");


//        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("BCDM");
        this.setSize(350, 450);
        this.setIconImage(imageIcon.getImage());
        this.setVisible(true);
//        this.setLayout(null);


        this.getContentPane().setBackground(Color.DARK_GRAY);
        JLabel jLabel = getjLabel();
//        jLabel.setBounds(0, 0, 250, 250);

        this.add(jLabel);
        this.pack();
    }

    private static JLabel getjLabel() {
        JLabel jLabel = new JLabel();
        ImageIcon logo = new ImageIcon("src/JFrames/assets/icon1.png");
        jLabel.setText("Welcome to Beulah's Concurrent Download Manager!");
        jLabel.setIcon(logo);
        jLabel.setForeground(Color.WHITE);
        jLabel.setHorizontalTextPosition(JLabel.CENTER);
        jLabel.setVerticalTextPosition(JLabel.BOTTOM);
        jLabel.setAlignmentY(JLabel.TOP);
//        jLabel.setFont(new Font("MV Boli", Font.PLAIN, 20));
        jLabel.setIconTextGap(0);
        jLabel.setVerticalAlignment(JLabel.TOP);
        jLabel.setHorizontalAlignment(JLabel.CENTER);
        return jLabel;
    }

    public String getFrameTitle() {
        return frameTitle;
    }

    public void setFrameTitle(String frameTitle) {
        this.frameTitle = frameTitle;
    }

//    public void showFrame() {
//        this.pack();
//        this.setVisible(true);
//    }
}
