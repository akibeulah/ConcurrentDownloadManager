package state;

import enums.MenuState;

public class AppStateManager {
    private static final AppStateManager instance = new AppStateManager();
    private MenuState menuState = MenuState.HOME;
    private DownloadScreenData downloadScreenData;
    private BCDMContainer bcdmContainer;
    private int numberOfChunks = 50;

    private AppStateManager() {}

    public static AppStateManager getInstance() {
        return instance;
    }

    public MenuState getMenuState() {
        return menuState;
    }

    public void setMenuState(MenuState menuState) {

        this.menuState = menuState;
    }

    public DownloadScreenData getDownloadScreenData() {
        return downloadScreenData;
    }

    public void setDownloadScreenData(DownloadScreenData downloadScreenData) {
        this.downloadScreenData = downloadScreenData;
    }

    public BCDMContainer getBcdmContainer() {
        return bcdmContainer;
    }

    public void setBcdmContainer(BCDMContainer bcdmContainer) {
        this.bcdmContainer = bcdmContainer;
    }

    public int getNumberOfChunks() {
        return numberOfChunks;
    }

    public void setNumberOfChunks(int numberOfChunks) {
        this.numberOfChunks = numberOfChunks;
    }
}

