package app;

import state.AppStateManager;
import ui.windows.MainWindow;

public class Main {
    public static void main(String[] args) {
        AppStateManager.loadStateFromFile();
        MainWindow mainWindow = new MainWindow();
        AppStateManager.getInstance().addDataChangeListener(mainWindow);
    }
}
