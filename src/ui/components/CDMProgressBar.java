package ui.components;

import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;

public class CDMProgressBar {
    public static class CustomProgressBarUI extends BasicProgressBarUI {
        @Override
        protected Color getSelectionForeground() {
            return Color.WHITE;
        }

        @Override
        protected Color getSelectionBackground() {
            return Color.WHITE;
        }
    }
}
