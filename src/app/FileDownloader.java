package app;

import enums.ChunkState;
import enums.FileState;
import models.CDMFile;
import models.Chunk;
import state.AppStateManager;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static utils.Utilities.NUM_THREADS_AND_CHUNKS;

public class FileDownloader {
    public static void downloadFile(CDMFile file) {
        AppLogger.info(String.format("Starting download for: %s", file.getID()));
        try {
            ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS_AND_CHUNKS);

            List<Future<Void>> futures = new ArrayList<>();

            for (int i = 0; i < NUM_THREADS_AND_CHUNKS; i++) {
                int finalI = i;
                Future<Void> future = executorService.submit(() -> {
                    Chunk chunk = file.getChunks().get(finalI);
                    chunk.setChunkState(ChunkState.DOWNLOADING);
                    downloadChunk(file.getUrl(), chunk);
                    return null;
                });

                futures.add(future);
            }
            file.setFileState(FileState.DOWNLOADING);
            AppStateManager.getInstance().notifyDataChangeListeners();

            file.setFutureList(futures);


            executorService.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void downloadChunk(String fileUrl, Chunk chunk) {
        try {
            HttpURLConnection connection = (HttpURLConnection) URI.create(fileUrl).toURL().openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setRequestProperty("Range", "bytes=" + chunk.getRangeStart() + "-" + chunk.getRangeEnd());

            try (InputStream inputStream = connection.getInputStream(); FileOutputStream outputStream = new FileOutputStream(chunk.getPath())) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            } finally {
                if (chunk.getPath().contains("CHK_049")) {
                    chunk.setCompletedSize(chunk.getLength());
                    AppStateManager.getInstance().unsafeSaveState();
                }
                connection.disconnect();
                chunk.setChunkState(ChunkState.COMPLETED);
            }
        } catch (IOException e) {
            e.printStackTrace();
            chunk.setChunkState(ChunkState.BROKEN);
        }
    }

    public static void pauseDownload(CDMFile cdmFile) {
        if (cdmFile.isFinalized())
            return;
        for (Future<Void> future : cdmFile.getFutureList())
            future.cancel(true);

        for (Chunk chunk : cdmFile.getChunks()) {
            chunk.setPausedLength(chunk.getLength());
            chunk.setChunkState(ChunkState.PAUSED);
        }

        cdmFile.setFileState(FileState.PAUSED);
        AppLogger.info("Paused " + cdmFile.getID());
    }

    public static void resumeDownload(CDMFile file) {
        if(file.getFileState() == FileState.DOWNLOADING)
            return;

        AppLogger.info(String.format("Resuming download for: %s\n", file.getID()));
        try {
            ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS_AND_CHUNKS);

            List<Future<Void>> futures = new ArrayList<>();

            for (int i = 0; i < NUM_THREADS_AND_CHUNKS; i++) {
                Chunk chunk = file.getChunks().get(i);

                if (chunk.getProgress() != 100 && chunk.getChunkState() != ChunkState.DOWNLOADING) {
                    Future<Void> future = executorService.submit(() -> {
                        chunk.setChunkState(ChunkState.DOWNLOADING);
                        resumeChunkDownload(file.getUrl(), chunk);
                        return null;
                    });

                    futures.add(future);
                }
            }

            file.setFileState(FileState.DOWNLOADING);
            AppStateManager.getInstance().notifyDataChangeListeners();

            file.setFutureList(futures);
            executorService.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void resumeChunkDownload(String fileUrl, Chunk chunk) {
        try {
            HttpURLConnection connection = (HttpURLConnection) URI.create(fileUrl).toURL().openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setRequestProperty("Range", "bytes=" + chunk.getPausedLength() + "-" + chunk.getRangeEnd());

            try (InputStream inputStream = connection.getInputStream(); FileOutputStream outputStream = new FileOutputStream(chunk.getPath(), true)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            } finally {
                if (chunk.getPath().contains("CHK_049")) {
                    chunk.setCompletedSize(chunk.getLength());
                    AppStateManager.getInstance().unsafeSaveState();
                }
                connection.disconnect();
                chunk.setChunkState(ChunkState.COMPLETED);
            }
        } catch (ConnectException e) {
            AppLogger.error(String.format("Failed to start download on: %s \nCheck internet connection", chunk.toString()));
            chunk.setChunkState(ChunkState.BROKEN);
        } catch (IOException e) {
            e.printStackTrace();
            AppLogger.error("Failed to start download on: " + chunk.toString());
            chunk.setChunkState(ChunkState.BROKEN);
        }
    }

    public static void mergeChunks(CDMFile cdmFile) throws IOException {
        AppLogger.info("Merging chinks for " + cdmFile.getID());
        try (FileOutputStream fos = new FileOutputStream(cdmFile.getOutputPath())) {
            for (Chunk chunk : cdmFile.getChunks()) {
                try (FileInputStream fis = new FileInputStream(chunk.getPath())) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;

                    // Read from the chunk and write to the merged file
                    while ((bytesRead = fis.read(buffer)) != -1) {
                        fos.write(buffer, 0, bytesRead);
                    }
                }
            }

            cdmFile.setFileState(FileState.COMPLETED);
            cdmFile.setFinalized(true);
            AppStateManager.getInstance().notifyDataChangeListeners();
            AppLogger.info(cdmFile.getID() + ": Chunks merged successfully into " + cdmFile.getOutputPath());
        } catch (FileNotFoundException e) {
            AppLogger.warning("Creating output dir: " + cdmFile.getOutputPath());
            Files.createDirectories(Paths.get(cdmFile.getOutputPath()).getParent());
            mergeChunks(cdmFile);
        } catch (IOException e) {
            e.printStackTrace();
            AppLogger.error("Failed to merge chunks for: " + cdmFile);
            cdmFile.setFileState(FileState.ERROR);
        }

        cdmFile.cleanUp();
        AppLogger.info(cdmFile.getID() + ": Download completed successfully!");
    }
}
