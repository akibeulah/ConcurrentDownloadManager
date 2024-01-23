package state;

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

    public void setAddNewUrl(boolean addNewUrl) throws IOException {
        this.addNewUrl = addNewUrl;
        this.saveStateToFile();
    }

    private void saveStateToFile() throws IOException {
        notifyDataChangeListeners();
        System.out.println("Change detected...");
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            if (file.createNewFile())
                System.out.println("Welcome to Beulah's Concurrent Download Manager!");
            else
                System.out.println("Welcome to Beulah's Concurrent Download Manager! There has been an error with our local data logging...");
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(instance);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    public static void loadStateFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            AppStateManager loadedInstance = (AppStateManager) ois.readObject();
            instance.setAllCDMFiles(loadedInstance.getAllCDMFiles());
            instance.setSelectedCDMFile(loadedInstance.getSelectedCDMFile());
            instance.setSelectedChunk(loadedInstance.getSelectedChunk());
            instance.setAddNewUrl(loadedInstance.isAddNewUrl());
        } catch (FileNotFoundException e) {
            // File not found, it's okay if it's the first run
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace(); // Handle the exception appropriately
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
