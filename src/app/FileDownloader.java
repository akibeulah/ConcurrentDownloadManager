package app;

import enums.FileState;
import models.CDMFile;
import models.Chunk;
import state.AppStateManager;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static utils.Utilities.NUM_THREADS_AND_CHUNKS;

public class FileDownloader {
    public static void downloadFile(CDMFile file) {
        System.out.printf("Starting download for: %s", file.getID());
        try {
            ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS_AND_CHUNKS);

            List<Future<Void>> futures = new ArrayList<>();

            for (int i = 0; i < NUM_THREADS_AND_CHUNKS; i++) {
                int finalI = i;
                Future<Void> future = executorService.submit(() -> {
                    downloadChunk(file.getUrl(), file.getChunks().get(finalI));
                    return null;
                });

                futures.add(future);
            }
            file.setFileState(FileState.DOWNLOADING);
            AppStateManager.getInstance().notifyDataChangeListeners();

            for (Future<Void> future : futures) {
                future.get();
            }

            executorService.shutdown();
            System.out.println("File download completed.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void downloadChunk(String fileUrl, Chunk chunk) {
        try {
            HttpURLConnection connection = (HttpURLConnection) URI.create(fileUrl).toURL().openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setRequestProperty("Range", "bytes=" + chunk.getRangeStart() + "-" + chunk.getRangeEnd());

            try (InputStream inputStream = connection.getInputStream();
                 FileOutputStream outputStream = new FileOutputStream(chunk.getPath())) {
//                FileOutputStream outputStream = new FileOutputStream("CHK_" + chunk.getRangeStart() + "-" + chunk.getRangeEnd() + ".bin")) {

                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                System.out.println("Downloaded chunk: " + chunk.getRangeStart() + "-" + chunk.getRangeEnd());

            } finally {
                connection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
