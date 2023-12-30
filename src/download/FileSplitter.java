package download;

import state.AppStateManager;
import state.BCDMContainer;
import state.DownloadScreenData;
import ui.CLIDisplay;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class FileSplitter {
    public static final int NUMBER_OF_THREADS = 5;

    public static void splitFile() {
        AppStateManager appStateManager = AppStateManager.getInstance();
        BCDMContainer bcdmContainer = appStateManager.getBcdmContainer();
        DownloadScreenData downloadScreenData = appStateManager.getDownloadScreenData();

        long fileSize = bcdmContainer.getFile().length();

        if (!new File(bcdmContainer.getChunksDirectory()).mkdir())
            return;

        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

        try (FileInputStream fis = new FileInputStream(bcdmContainer.getFile().getPath())) {
            byte[] buffer = new byte[(int) Math.ceil((double) fileSize / 50)];
            int bytesRead;
            AtomicLong totalBytesWritten = new AtomicLong();

            int chunkNumber = 0;
            List<Future<Boolean>> futures = new ArrayList<>();

            Thread run = CLIDisplay.screenThread();
            run.start();
            
            while ((bytesRead = fis.read(buffer)) > 0) {
                String chunkFileName = String.format("%s/chunk_%03d", bcdmContainer.getChunksDirectory(), chunkNumber);

                int finalBytesRead = bytesRead;

                Callable<Boolean> splitTask = () -> {
                    try (FileOutputStream fos = new FileOutputStream(chunkFileName)) {
                        fos.write(buffer, 0, finalBytesRead);
                        totalBytesWritten.addAndGet(finalBytesRead);

                        int chunkProgress = (int) ((double) totalBytesWritten.get() / fileSize * 100);
                        downloadScreenData.setTotalFileProgress(chunkProgress);
                        appStateManager.setDownloadScreenData(downloadScreenData);
                        return true;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return false;
                    }
                };

                Future<Boolean> future = executorService.submit(splitTask);
                futures.add(future);
                chunkNumber++;
            }

            for (Future<Boolean> future : futures) {
                try {
                    future.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }

    }
}
