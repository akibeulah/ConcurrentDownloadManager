package app;

import download.FileSplitter;
import jdk.jshell.spi.ExecutionControl;
import state.AppStateManager;
import state.BCDMContainer;
import state.DownloadScreenData;
import ui.CLIDisplay;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException, ExecutionControl.NotImplementedException, InterruptedException {
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
                int[] chunkProgress = new int[50];
                DownloadScreenData downloadData = new DownloadScreenData(
                        "SampleFile.txt",
                        "12323299291911",
                        "https://example.com/samplefile.txt",
                        "/downloads/chunks",
                        "/output/directory",
                        chunkProgress
                );

                CLIDisplay.displayDownloadScreen(downloadData, true);
            default:
                break;
        }
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