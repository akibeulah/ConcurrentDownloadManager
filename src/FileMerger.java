import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileMerger {

    public static boolean mergeChunks(BCDMContainer bcdmContainer) {
        final String outputFilePath = "./output/" + bcdmContainer.file.getName();
        File chunksDir = new File(bcdmContainer.chunksDirectory);

        List<String> chunkPaths = new ArrayList<String>();
        for (final File file: Objects.requireNonNull(chunksDir.listFiles())) {
            chunkPaths.add(file.getPath());
        }

        try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
            for (String cP : chunkPaths) {
                try (FileInputStream fis = new FileInputStream(cP)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;

                    // Read from the chunk and write to the merged file
                    while ((bytesRead = fis.read(buffer)) != -1) {
                        fos.write(buffer, 0, bytesRead);
                    }
                }
            }

            System.out.println("Chunks merged successfully into " + outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        bcdmContainer.deleteChunksDirectory();
        return true;
    }
}
