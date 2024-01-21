package app;

import state.AppStateManager;
import ui.windows.MainWindow;

public class Main {
    public static void main(String[] args) {
        AppStateManager.loadStateFromFile();
        new MainWindow();
    }
}
