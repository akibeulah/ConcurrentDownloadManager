package state;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

public class BCDMContainer {
    File file;
    String chunksDirectory;
    String outputDirectory;
    int[] chunksProgress;

    public BCDMContainer(File file, String outputDirectory) {
        final String PARENT_CHUNKS_DIR = "./chunks/";
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

        this.file = file;
        this.chunksDirectory = String.format("%s%s", PARENT_CHUNKS_DIR, timeStamp);
        this.outputDirectory = outputDirectory;
        this.chunksProgress = new int[50];
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getChunksDirectory() {
        return chunksDirectory;
    }

    public void setChunksDirectory(String chunksDirectory) {
        this.chunksDirectory = chunksDirectory;
    }

    public void deleteChunksDirectory() {
        deleteDirectory(new File(chunksDirectory));
    }

    public static void deleteDirectory(File file) {
        for (File subfile : Objects.requireNonNull(file.listFiles())) {
            if (subfile.isDirectory()) {
                deleteDirectory(subfile);
            }
            subfile.delete();
        }
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }
}