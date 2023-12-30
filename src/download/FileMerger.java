package download;

import state.AppStateManager;
import state.BCDMContainer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileMerger {

    public static void mergeChunks() {
        AppStateManager appStateManager = AppStateManager.getInstance();
        BCDMContainer bcdmContainer = appStateManager.getBcdmContainer();
        File chunksDir = new File(bcdmContainer.getChunksDirectory());

        List<String> chunkPaths = new ArrayList<String>();
        for (final File file: Objects.requireNonNull(chunksDir.listFiles())) {
            chunkPaths.add(file.getPath());
        }

        try (FileOutputStream fos = new FileOutputStream(bcdmContainer.getOutputDirectory())) {
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

            System.out.println("Chunks merged successfully into " + bcdmContainer.getOutputDirectory());
        } catch (IOException e) {
            e.printStackTrace();
        }

        bcdmContainer.deleteChunksDirectory();
        System.out.println("Download completed successfully!");
    }
}
