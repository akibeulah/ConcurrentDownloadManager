package state;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class DownloadScreenData {
    String fileName;
    String fileSize;
    String fileURL;
    String chunksDir;
    String outputDirectory;
    int[] chunkProgress;
    long byteLength;
    int totalFileProgress;
    public DownloadScreenData(String fileName, String fileSize, String fileURL, String chunksDir, String outputDirectory, int[] chunkProgress) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileURL = fileURL;
        this.chunksDir = chunksDir;
        this.outputDirectory = outputDirectory;
        this.chunkProgress = chunkProgress;
        this.totalFileProgress = 0;
    }

    public DownloadScreenData(BCDMContainer bcdmContainer) throws IOException {
        this.fileName = bcdmContainer.getFile().getName();
        this.fileSize = String.valueOf(bcdmContainer.getFile().length());
        this.fileURL = bcdmContainer.getFile().getCanonicalPath();
        this.chunksDir = bcdmContainer.getChunksDirectory();
        this.outputDirectory = bcdmContainer.getOutputDirectory();
        this.chunkProgress = bcdmContainer.chunksProgress;
        this.totalFileProgress = 0;
    }

    public int getTotalFileProgress() {
        return totalFileProgress;
    }

    public void setTotalFileProgress(int totalFileProgress) {
        this.totalFileProgress = totalFileProgress;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileURL() {
        return fileURL;
    }

    public void setFileURL(String fileURL) {
        this.fileURL = fileURL;
    }

    public String getChunksDir() {
        return chunksDir;
    }

    public void setChunksDir(String chunksDir) {
        this.chunksDir = chunksDir;
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public int[] getChunkProgress() {
        return chunkProgress;
    }

    public void setChunkProgress(int[] chunkProgress) {
        this.chunkProgress = chunkProgress;
    }

    public void updateChunkProgressByIndex(int idx, int progress) {
        int[] tempChkPgress = this.getChunkProgress();
        tempChkPgress[idx] = progress;

        setChunkProgress(tempChkPgress);
    }

    @Override
    public String toString() {
        return "DownloadScreenData{" +
                "fileName='" + fileName + '\'' +
                ", fileSize='" + fileSize + '\'' +
                ", fileURL='" + fileURL + '\'' +
                ", chunksDir='" + chunksDir + '\'' +
                ", outputDirectory='" + outputDirectory + '\'' +
                ", chunkProgress=" + Arrays.toString(chunkProgress) +
                ", byteLength=" + byteLength +
                ", totalFileProgress=" + totalFileProgress +
                '}';
    }

    public int updateChunkProgress(int idx) {
        AppStateManager appStateManager = AppStateManager.getInstance();

        String desiredFileName = String.format("\\chunk_%03d", idx);
        File desiredFile = new File(getChunksDir() + desiredFileName);

        chunkProgress[idx] = (int) (desiredFile.length() * 100 / (Long.parseLong(fileSize) / appStateManager.getNumberOfChunks()));
        return chunkProgress[idx];
    }
}
