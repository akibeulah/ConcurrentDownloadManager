package models;

import app.AppLogger;
import app.FileDownloader;
import enums.FileState;
import state.AppStateManager;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import static utils.Utilities.*;

public class CDMFile implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String ID;
    private String name;
    private String url;
    private URI uri;
    private long size;
    private List<Chunk> chunks;
    private String chunksPath;
    private String outputPath;
    private transient FileState fileState = FileState.INITIALIZING;
    private boolean finalized = false;
    private transient List<Future<Void>> futureList;

    public CDMFile(String url) throws URISyntaxException, IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        this.ID = String.format("%s_%s", timeStamp, UUID.randomUUID());
        this.url = url;
        this.uri = URI.create(url);
        this.chunks = new ArrayList<Chunk>();
        this.chunksPath = PARENT_CHUNKS_DIR + timeStamp;

        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        connection.setRequestMethod("HEAD");
        connection.connect();

        if (connection.getResponseCode() / 100 == 2) {
            this.size = connection.getContentLength();
            this.name = url.split("/")[url.split("/").length - 1];
            Path path;

            try {
                if (name.length() > 20)
                    throw new InvalidPathException("", "Name too long!");
                path = Paths.get(chunksPath + String.format("%s", name));
            } catch (InvalidPathException e) {
                AppLogger.warning("Illegal name, using ID");
                this.name = ID;
                path = Paths.get(chunksPath + String.format("%s", ID));
            }
            Files.createDirectories(path.getParent());

            long chunkSize = (long) Math.ceil((double) size / NUM_THREADS_AND_CHUNKS);
            for (int i = 0; i < NUM_THREADS_AND_CHUNKS; i++) {
                long startByte = i * chunkSize;
                Chunk chunk;
                String chunkPath = String.format("%s/CHK_%03d", chunksPath, i);
                if (i == NUM_THREADS_AND_CHUNKS - 1) {
                    chunk = new Chunk(chunkPath, chunkSize, String.valueOf(startByte), String.valueOf(size - 1));
                } else {
                    chunk = new Chunk(chunkPath, chunkSize, String.valueOf(startByte), String.valueOf((i + 1) * chunkSize - 1));
                }
                chunks.add(chunk);
                path = Paths.get(chunkPath);
                Files.createDirectories(path.getParent());
                Files.createFile(path);
            }

        } else {
            this.size = -1;
            this.name = String.valueOf(UUID.randomUUID());
            AppLogger.warning(String.valueOf(connection));
            AppLogger.warning("Unable to retrieve file information. Server response code: " + connection.getResponseCode());
        }

        this.outputPath = String.format("%s%s_%s", OUTPUT_DIR, timeStamp, name);
        this.fileState = FileState.INITIALIZING;

        connection.disconnect();
        startDownload();
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public boolean isFinalized() {
        return finalized;
    }

    public void setFinalized(boolean finalized) {
        this.finalized = finalized;
    }

    public List<Future<Void>> getFutureList() {
        return futureList;
    }

    public void setFutureList(List<Future<Void>> futureList) {
        this.futureList = futureList;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public List<Chunk> getChunks() {
        return chunks;
    }

    public void setChunks(List<Chunk> chunks) {
        this.chunks = chunks;
    }

    public String getChunksPath() {
        return chunksPath;
    }

    public void setChunksPath(String chunksPath) {
        this.chunksPath = chunksPath;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public FileState getFileState() {
        return fileState;
    }

    public void setFileState(FileState fileState) {
        this.fileState = fileState;
    }

    public long getProgress() {
        AtomicLong res = new AtomicLong();
        this.chunks.forEach((chunk) -> {
            res.addAndGet(chunk.getProgress());
        });
        return res.get() / NUM_THREADS_AND_CHUNKS;
    }

    public boolean areChunksCompleted() {
        AtomicBoolean res = new AtomicBoolean(true);
        javaIsSoWeirdForThisForLoopLabelThingThatIJustReadAbout:
        for (Chunk chunk : this.chunks) {
            if (chunk.getProgress() != 100) {
                res.set(false);
                break;
            }
        }

        return res.get();
    }

    public void startDownload() {
        FileDownloader.downloadFile(this);
    }

    public void pauseDownload() {
        FileDownloader.pauseDownload(this);
    }

    public void resumeDownload() {
        FileDownloader.resumeDownload(this);
    }

    @Override
    public String toString() {
        return "\nCDMFile{" + "\n\t" +
                "ID='" + ID + '\'' + ",\n\t" +
                "name='" + name + '\'' + ",\n\t" +
                "url='" + url + '\'' + ",\n\t" +
                "uri=" + uri + ",\n\t" +
                "size=" + size + ",\n\t" +
                "chunks=" + chunks.toString().replaceAll("\n", "\n\t\t") + ",\n\t" +
                "chunksPath='" + chunksPath + '\'' + ",\n\t" +
                "outputPath='" + outputPath + '\'' + ",\n\t" +
                "progress=" + getProgress() + ",\n\t" +
                "fileState=" + fileState + "\n" +
                '}' + "\n";
    }

    public void checkData() {
        AppLogger.info("Checking data: " + ID);
        try {
            if (this.areChunksCompleted()) {
                AppLogger.info("CDMFile Finalizing: " + ID);
                setFileState(FileState.FINALIZING);
                AppStateManager.getInstance().notifyDataChangeListeners();
                FileDownloader.mergeChunks(this);
            } else {
                if (this.getFileState() == FileState.DOWNLOADING)
                    this.resumeDownload();
            }
        } catch (Exception e) {
            setFileState(FileState.ERROR);
        }
    }

    public void checkIfReadyForMerging() {
        AppLogger.info("Checking data: " + ID);
        try {
            if (this.areChunksCompleted()) {
                AppLogger.info("CDMFile Finalizing: " + ID);
                setFileState(FileState.FINALIZING);
                AppStateManager.getInstance().notifyDataChangeListeners();
                FileDownloader.mergeChunks(this);
            }
        } catch (Exception e) {
            setFileState(FileState.ERROR);
        }
    }

    public void cleanUp() {

    }
}
