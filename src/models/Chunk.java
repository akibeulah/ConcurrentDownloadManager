package models;

import enums.ChunkState;

import java.io.File;
import java.io.Serial;
import java.io.Serializable;

public class Chunk implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String path;
    private transient ChunkState chunkState = ChunkState.INITIALIZING;
    private final String rangeStart;
    private final String rangeEnd;
    private long completedSize;
    private long pausedLength;

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

    public long getPausedLength() {
        return pausedLength;
    }

    public void setPausedLength(long pausedLength) {
        this.pausedLength = pausedLength;
    }

    public void setCompletedSize(long completedSize) {
        this.completedSize = completedSize;
    }

    public String getRangeStart() {
        return rangeStart;
    }

    public String getRangeEnd() {
        return rangeEnd;
    }

    public void setChunkState(ChunkState chunkState) {
        this.chunkState = chunkState;
    }

    public String getPath() {
        return path;
    }

    public int getProgress() {
        long length = getLength();
        if (length == 0)
            return 0;
        else {
            long res = (length * 100) / completedSize;
            return (int) res;
        }
    }

    public long getLength() {
        File chunk = new File(path);
        if (chunk.exists())
            return chunk.length();
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
                "completedSize=" + completedSize + "\n\t" +
                "currentSize=" + getLength() + "\n" +
                '}';
    }
}
