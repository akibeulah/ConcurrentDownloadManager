package download;

import java.net.HttpURLConnection;
import java.net.URL;

public class FileDownloader {

    public static long getFileSize(URL fileUrl) {
        long fileSize = -1;

        try {
            HttpURLConnection connection = (HttpURLConnection) fileUrl.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setRequestMethod("HEAD");
            connection.connect();

            // Check if the response code indicates success (2xx)
            if (connection.getResponseCode() / 100 == 2) {
                // Retrieve the content length from the headers
                fileSize = connection.getContentLength();

                if (fileSize > 0) {
                    System.out.println("File size: " + fileSize + " bytes");
                } else {
                    System.out.println("Unable to determine file size.");
                }
            } else {
                System.out.println(connection);
                System.out.println("Unable to retrieve file information. Server response code: " + connection.getResponseCode());
            }

            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileSize;
    }

    public static void downloadFile(int fileSize, URL fileUrl) {

        try {
            HttpURLConnection connection = (HttpURLConnection) fileUrl.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setRequestMethod("HEAD");
            connection.connect();

            // Check if the response code indicates success (2xx)
            if (connection.getResponseCode() / 100 == 2) {
                // Retrieve the content length from the headers
                fileSize = connection.getContentLength();

                if (fileSize > 0) {
                    System.out.println("File size: " + fileSize + " bytes");
                } else {
                    System.out.println("Unable to determine file size.");
                }
            } else {
                System.out.println(connection);
                System.out.println("Unable to retrieve file information. Server response code: " + connection.getResponseCode());
            }

            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
