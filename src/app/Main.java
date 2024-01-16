package app;

import download.FileDownloader;
import download.FileSplitter;
import jdk.jshell.spi.ExecutionControl;
import state.AppStateManager;
import state.BCDMContainer;
import state.DownloadScreenData;
import ui.CLIDisplay;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException, ExecutionControl.NotImplementedException, InterruptedException, URISyntaxException {
        CLIDisplay.printHomeScreen();
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                throw new ExecutionControl.NotImplementedException("Still a work in progress! Coming soon...");
            case 2:
                testApplication();
                break;
            case 3:
                testApplicationOnline();
            default:
                break;
        }
    }

    private static void testApplicationOnline() throws URISyntaxException, MalformedURLException {
        AppStateManager appStateManager = AppStateManager.getInstance();

//        final String FILE_URL = "https://singapore.downloadtestfile.com/5GB.zip?_gl=1*6wr4fy*_ga*NjcyNzAxODQzLjE3MDQwMTQxOTc.*_ga_ZRTNSEE7YV*MTcwNDAxNDE5Ni4xLjEuMTcwNDAxNDIwOC4wLjAuMA..";
        final String FILE_URL = "http://ipv4.download.thinkbroadband.com/50MB.zip";

        URL url = new URL(FILE_URL);
        System.out.println(FileDownloader.getFileSize(url));
    }

    private static void testApplication() throws IOException, InterruptedException {
        AppStateManager appStateManager = AppStateManager.getInstance();

        final String FILE_PATH = "C:\\Users\\akibe\\Downloads\\Scott Pilgrim vs the World (2010) [1080p]\\Scott.Pilgrim.vs.the.World.2010.Bluray.1080p.x264.YIFY.mp4";
        File testFile = new File(FILE_PATH);
        BCDMContainer bcdmContainer = new BCDMContainer(testFile, "./output/" + testFile.getName());
        appStateManager.setBcdmContainer(bcdmContainer);

        DownloadScreenData downloadScreenData = new DownloadScreenData(appStateManager.getBcdmContainer());
        appStateManager.setDownloadScreenData(downloadScreenData);
        FileSplitter.splitFile();
    }
}