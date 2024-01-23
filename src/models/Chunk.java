package models;

import enums.ChunkState;

import java.io.File;
import java.io.Serial;
import java.io.Serializable;

public class Chunk implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String path;
    private ChunkState chunkState;
    private String rangeStart;
    private String rangeEnd;
    private long completedSize;

    public Chunk(String path, long completedSize, String rangeStart, String rangeEnd) {
        this.path = path;
        this.completedSize = completedSize;
        this.chunkState = ChunkState.INITIALIZING;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
    }

    public long getCompletedSize() {
        return completedSize;
    }

    public void setCompletedSize(long completedSize) {
        this.completedSize = completedSize;
    }

    public String getRangeStart() {
        return rangeStart;
    }

    public void setRangeStart(String rangeStart) {
        this.rangeStart = rangeStart;
    }

    public String getRangeEnd() {
        return rangeEnd;
    }

    public void setRangeEnd(String rangeEnd) {
        this.rangeEnd = rangeEnd;
    }

    public void setChunkState(ChunkState chunkState) {
        this.chunkState = chunkState;
    }

    public String getPath() {
        return path;
    }

    public int getProgress() {
        File chunk = new File(path);
        if (chunk.exists())
            return (int) (chunk.length() / completedSize) * 100;
        else
            return 0;
    }

    public ChunkState getChunkState() {
        return chunkState;
    }

    @Override
    public String toString() {
        return "\nChunk{" + "\n\t" +
                "path='" + path + '\'' + ",\n\t" +
                "progress=" + getProgress() + ",\n\t" +
                "chunkState=" + chunkState + ",\n\t" +
                "rangeStart='" + rangeStart + '\'' + ",\n\t" +
                "rangeEnd='" + rangeEnd + '\'' + ",\n\t" +
                "completedSize=" + completedSize + "\n" +
                '}' + "\n";
    }
}
