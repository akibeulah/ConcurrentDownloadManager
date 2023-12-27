import java.io.*;

public class FileSplitter {
    public static boolean splitFile(BCDMContainer bcdmContainer) {
        long fileSize = bcdmContainer.file.length();

        if (!new File(bcdmContainer.chunksDirectory).mkdir())
            return false;

        try (FileInputStream fis = new FileInputStream(bcdmContainer.file.getPath())) {
            byte[] buffer = new byte[(int) Math.ceil((double) fileSize / 50)];
            int bytesRead;

            int chunkNumber = 0;
            while ((bytesRead = fis.read(buffer)) > 0) {
                String chunkFileName = String.format("%s/chunk_%03d", bcdmContainer.chunksDirectory, chunkNumber);
                try (FileOutputStream fos = new FileOutputStream(chunkFileName)) {
                    fos.write(buffer, 0, bytesRead);
                }
                chunkNumber++;
            }

            System.out.println("File split successfully into " + chunkNumber + " chunks.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }
}
