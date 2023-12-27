import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class BCDMContainer {
    File file;
    String chunksDirectory;

    public BCDMContainer(File file) {
        final String PARENT_CHUNKS_DIR = "./chunks/";
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

        this.file = file;
        this.chunksDirectory = String.format("%s%s", PARENT_CHUNKS_DIR, timeStamp);
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

    public static void deleteDirectory(File file)
    {
        for (File subfile : Objects.requireNonNull(file.listFiles())) {
            if (subfile.isDirectory()) {
                deleteDirectory(subfile);
            }
            subfile.delete();
        }
    }
}