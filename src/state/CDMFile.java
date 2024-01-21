package state;

import enums.FileState;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import static utils.Utilities.*;

public class CDMFile implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String ID;
    private String name;
    private String url;
    private URI uri;
    private long size;
    private List<Chunk> chunks;
    private String chunksPath;
    private String outputPath;
    private int progress;
    private FileState fileState;

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

            long chunkSize = (long) Math.ceil((double) size / NUM_THREADS_AND_CHUNKS);
            for (int i = 0; i < NUM_THREADS_AND_CHUNKS; i++) {
                long startByte = i * chunkSize;
                long endByte;
                Chunk chunk;
                String chunkPath = String.format("%s/CHK_%02d", chunksPath, i);
                if (i == NUM_THREADS_AND_CHUNKS - 1) {
                    chunk = new Chunk(chunkPath, chunkSize, String.valueOf(startByte), String.valueOf(size - 1));
                } else {
                    chunk = new Chunk(chunkPath, chunkSize, String.valueOf(startByte), String.valueOf((i + 1) * chunkSize - 1));
                }
                chunks.add(chunk);
            }

        } else {
            this.size = -1;
            this.name = String.valueOf(UUID.randomUUID());
            System.out.println(connection);
            System.out.println("Unable to retrieve file information. Server response code: " + connection.getResponseCode());
        }

        this.outputPath = String.format("%s%s_%s", OUTPUT_DIR, timeStamp, name);
        this.progress = 0;
        this.fileState = FileState.INITIALIZING;

        connection.disconnect();
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
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

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public FileState getFileState() {
        return fileState;
    }

    public void setFileState(FileState fileState) {
        this.fileState = fileState;
    }

    @Override
    public String toString() {
        return "\nCDMFile{" + "\n\t" +
                "ID='" + ID + '\'' + ",\n\t" +
                " name='" + name + '\'' + ",\n\t" +
                " url='" + url + '\'' + ",\n\t" +
                " uri=" + uri + ",\n\t" +
                " size=" + size + ",\n\t" +
                " chunks=" + chunks + ",\n\t" +
                " chunksPath='" + chunksPath + '\'' + ",\n\t" +
                " outputPath='" + outputPath + '\'' + ",\n\t" +
                " progress=" + progress + ",\n\t" +
                " fileState=" + fileState + "\n" +
                '}' + "\n";
    }
}
