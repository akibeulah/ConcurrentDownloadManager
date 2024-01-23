package state;

import app.AppLogger;
import models.CDMFile;
import models.Chunk;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static utils.Utilities.FILE_PATH;

public class AppStateManager implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final AppStateManager instance = new AppStateManager();
    private List<CDMFile> allCDMFiles = new ArrayList<CDMFile>();
    private transient String selectedCDMFile = null;
    private transient Chunk selectedChunk;
    private boolean addNewUrl = false;
    private transient final List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    public void addDataChangeListener(DataChangeListener listener) {
        dataChangeListeners.add(listener);
    }

    public void notifyDataChangeListeners() {
        List<DataChangeListener> listenersCopy = new ArrayList<>(dataChangeListeners);

        for (DataChangeListener listener : listenersCopy) {
            listener.onDataChanged();
        }
    }

    public int getDataChangeListeners() {
        return dataChangeListeners.size();
    }

    public CDMFile getCDMFile(String id) {
        return allCDMFiles.stream().filter(file -> Objects.equals(file.getID(), id)).findFirst().orElse(null);
    }

    private AppStateManager() {
    }

    public static AppStateManager getInstance() {
        return instance;
    }

    public List<CDMFile> getAllCDMFiles() {
        return allCDMFiles;
    }

    public void setAllCDMFiles(List<CDMFile> allCDMFiles) throws IOException {
        this.allCDMFiles = allCDMFiles;
        this.saveStateToFile();
    }

    public void addCMDFile(CDMFile file) throws IOException {
        this.allCDMFiles.add(file);
        this.saveStateToFile();
    }

    public String getSelectedCDMFile() {
        return selectedCDMFile;
    }

    public void setSelectedCDMFile(String selectedCDMFileID) throws IOException {
        this.selectedCDMFile = selectedCDMFileID;
        this.saveStateToFile();
    }

    public Chunk getSelectedChunk() {
        return selectedChunk;
    }

    public void setSelectedChunk(Chunk selectedChunk) throws IOException {
        this.selectedChunk = selectedChunk;
        this.saveStateToFile();
    }

    public boolean isAddNewUrl() {
        return addNewUrl;
    }

    public void unsafeSaveState() throws IOException {
        saveStateToFile();
    }

    public void setAddNewUrl(boolean addNewUrl) throws IOException {
        this.addNewUrl = addNewUrl;
        this.saveStateToFile();
    }

    private void saveStateToFile() throws IOException {
        notifyDataChangeListeners();
        AppLogger.info("Change detected...");
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            if (file.createNewFile())
                AppLogger.info("Welcome to Beulah's Concurrent Download Manager!");
            else
                AppLogger.info("Welcome to Beulah's Concurrent Download Manager! There has been an error with our local data logging...");
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(instance);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    public static void loadStateFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            AppLogger.info("Loading state");
            AppStateManager loadedInstance = (AppStateManager) ois.readObject();
            instance.setAllCDMFiles(loadedInstance.getAllCDMFiles());
            instance.setAddNewUrl(loadedInstance.isAddNewUrl());

            for (CDMFile cdmFile : instance.allCDMFiles) {
                cdmFile.checkData();
            }
        } catch (FileNotFoundException e) {
            AppLogger.warning("File not found, should be fine but might be an indicator of file storage issues...");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "\nAppStateManager{" + "\n\t" +
                "allCDMFiles=" + allCDMFiles.toString().replaceAll("\n", "\n\t\t") + ",\n\t" +
                "selectedCDMFile=" + selectedCDMFile + ",\n\t" +
                "selectedChunk=" + selectedChunk + ",\n\t" +
                "addNewUrl=" + addNewUrl + "\n" +
                '}' + "\n";
    }
}
