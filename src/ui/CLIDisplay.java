package ui;

import download.FileMerger;
import state.AppStateManager;
import state.DownloadScreenData;

import java.io.IOException;

public class CLIDisplay {
    private static final String ANSI_CLEAR_SCREEN = "\u001B[2J";
    private static final String ANSI_HOME_CURSOR = "\u001B[H";

    public static void clearScreen() {
        try {
            final String os = System.getProperty("os.name").toLowerCase();

            if (os.contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void refreshScreen() {
        clearScreen();
//        System.out.print(ANSI_HOME_CURSOR);
    }

    public static void printHomeScreen() throws IOException, InterruptedException {
        refreshScreen();
        printLogo();

        printAtPosition("Welcome to Beulah's Concurrent Download Manager!", 10);
        printAtPosition("------------------------------------------------", 11);
        printAtPosition("Usage: BCDM -u <url>", 12);
        printAtPosition("1. Download file from URL", 13);
        printAtPosition("2. Test Application (Scott Pilgrim Video)", 14);
        printAtPosition("3. Test Application Online", 15);
    }

    public static void printAtPosition(String text, int y) {
        System.out.print("\u001B[" + y + ";1H");
        System.out.println(text);
    }

    public static void printAtPosition(String text, int x, int y) {
        System.out.print("\u001B[" + y + ";" + x + "H");
        System.out.println(text);
    }

    public static void displayDownloadScreen(DownloadScreenData downloadData, boolean firstPaint) throws IOException, InterruptedException {
        if (firstPaint) {
            refreshScreen();
            printDownloadScreenHeader(downloadData);
        }

        // Print chunk progress (update only the percentages)
        printChunkProgress(downloadData, firstPaint);
    }

    private static void printDownloadScreenHeader(DownloadScreenData downloadData) {
        printLogo();
        int counter = 9;
        printAtPosition("================================================================================================================", counter++);
        printAtPosition("File Name\t\t\t| " + downloadData.getFileName(), counter++);
        printAtPosition("Size\t\t\t\t| " + downloadData.getFileSize(), counter++);
        printAtPosition("File URL\t\t\t| " + downloadData.getFileURL(), counter++);
        printAtPosition("Chunks Dir\t\t\t| " + downloadData.getChunksDir(), counter++);
        printAtPosition("Output Directory\t\t| " + downloadData.getOutputDirectory(), counter++);
        printAtPosition("================================================================================================================", counter++);
    }

    private static void printChunkProgress(DownloadScreenData downloadData, boolean firstPaint) {
        int xCounter = 1, yCounter = 17;
        int[] chunkProgress = downloadData.getChunkProgress();

        if (firstPaint) {
            StringBuilder chkProgress = new StringBuilder();

            for (int i = 0; i < chunkProgress.length; i++) {
                String chunkLabel = "CHK" + String.format("%03d", i);
                String progressBar = generateProgressBar(downloadData.updateChunkProgress(i));

                chkProgress.append(chunkLabel).append(" ").append(progressBar).append("    ");
                printAtPosition(
                        String.valueOf(chkProgress),
                        xCounter == 1 ? 1: 15 * (xCounter - 1),
                        yCounter
                );
                chkProgress = new StringBuilder();

                if ((i + 1) % 5 == 0) {
                    yCounter++;
                    xCounter = 1;
                } else {
                    xCounter++;
                }
            }
        } else {
            for (int i = 0; i < chunkProgress.length; i++) {
                printAtPosition(
                        String.valueOf(downloadData.updateChunkProgress(i)),
                        xCounter == 1 ? 8 + 1 : (15 * (xCounter - 1) + 8),
                        yCounter
                );

                if ((i + 1) % 5 == 0) {
                    yCounter++;
                    xCounter = 1;
                } else {
                    xCounter++;
                }
            }
        }

        String chunkLabel = "TOTAL: ";
        String progressBar = generateProgressBar(downloadData.getTotalFileProgress());
        String line = chunkLabel + " " + progressBar;
        printAtPosition(line, yCounter + 1);
    }

    private static String generateProgressBar(int progress) {
        // StringBuilder progressBar = new StringBuilder();
        //        int est = (int) Math.floor((double) (progress * 50) / 100);

//        if (est < 1) {
//            progressBar.append("--------------------------------------------------");
//        } else {
//            progressBar.append("#".repeat(Math.max(0, est)));
//            progressBar.append("-".repeat(Math.max(0, Math.abs(50 - est))));
//        }
        return String.format(" %03d%%", progress);
    }

    public static void printLogo() {
        String asciiArt =
                "oooooooooo.        .oooooo.       oooooooooo.       ooo        ooooo\0" +
                        "`888'   `Y8b      d8P'  `Y8b      `888'   `Y8b      `88.       .888'\0" +
                        " 888     888     888               888      888      888b     d'888 \0" +
                        " 888oooo888'     888               888      888      8 Y88. .P  888  \0" +
                        " 888    `88b     888               888      888      8  `888'   888  \0" +
                        " 888    .88P     `88b    ooo       888     d88'      8    Y     888  \0" +
                        "o888bood8P'       `Y8bood8P'      o888bood8P'       o8o        o888o \0" +
                        "                                                                      \0" +
                        "                                                                      ";

        // Split the ASCII art into lines
        String[] lines = asciiArt.split("\0");

        // Print each line at a specific position
        for (int i = 0; i < lines.length; i++) {
            printAtPosition(lines[i], i + 1);
        }
    }

    public static void printEasterEggLogo() {
        clearScreen();
        String logo =
                "          _____                            _____                            _____                            _____          \n" +
                        "         /\\    \\                          /\\    \\                          /\\    \\                          /\\    \\         \n" +
                        "        /::\\    \\                        /::\\    \\                        /::\\    \\                        /::\\____\\        \n" +
                        "       /::::\\    \\                      /::::\\    \\                      /::::\\    \\                      /::::|   |        \n" +
                        "      /::::::\\    \\                    /::::::\\    \\                    /::::::\\    \\                    /:::::|   |        \n" +
                        "     /:::/\\:::\\    \\                  /:::/\\:::\\    \\                  /:::/\\:::\\    \\                  /::::::|   |        \n" +
                        "    /:::/__\\:::\\    \\                /:::/  \\:::\\    \\                /:::/  \\:::\\    \\                /:::/|::|   |        \n" +
                        "   /::::\\   \\:::\\    \\              /:::/    \\:::\\    \\              /:::/    \\:::\\    \\              /:::/ |::|   |        \n" +
                        "  /::::::\\   \\:::\\    \\            /:::/    / \\:::\\    \\            /:::/    / \\:::\\    \\            /:::/  |::|___|______  \n" +
                        " /:::/\\:::\\   \\:::\\ ___\\          /:::/    /   \\:::\\    \\          /:::/    /   \\:::\\ ___\\          /:::/   |::::::::\\    \\ \n" +
                        "/:::/__\\:::\\   \\:::|    |        /:::/____/     \\:::\\____\\        /:::/____/     \\:::|    |        /:::/    |:::::::::\\____\\\n" +
                        "\\:::\\   \\:::\\  /:::|____|        \\:::\\    \\      \\::/    /        \\:::\\    \\     /:::|____|        \\::/    / ~~~~~/:::/    /\n" +
                        " \\:::\\   \\:::\\/:::/    /          \\:::\\    \\      \\/____/          \\:::\\    \\   /:::/    /          \\/____/      /:::/    / \n" +
                        "  \\:::\\   \\::::::/    /            \\:::\\    \\                       \\:::\\    \\ /:::/    /                       /:::/    /  \n" +
                        "   \\:::\\   \\::::/    /              \\:::\\    \\                       \\:::\\    /:::/    /                       /:::/    /   \n" +
                        "    \\:::\\  /:::/    /                \\:::\\    \\                       \\:::\\  /:::/    /                       /:::/    /    \n" +
                        "     \\:::\\/:::/    /                  \\:::\\    \\                       \\:::\\/:::/    /                       /:::/    /     \n" +
                        "      \\::::::/    /                    \\:::\\    \\                       \\::::::/    /                       /:::/    /      \n" +
                        "       \\::::/    /                      \\:::\\____\\                       \\::::/    /                       /:::/    /       \n" +
                        "        \\::/____/                        \\::/    /                        \\::/____/                        \\::/    /        \n" +
                        "         ~~                               \\/____/                          ~~                               \\/____/         \n" +
                        "                                                                                                                            ";
        printAtPosition(logo, 1);
    }

    public static Thread screenThread() {
        Runnable screenThread = () -> {
            AppStateManager appStateManager = AppStateManager.getInstance();
            DownloadScreenData downloadScreenData = appStateManager.getDownloadScreenData();
            boolean firstPaint = true;

            while (downloadScreenData.getTotalFileProgress() < 100) {
                try {
                    CLIDisplay.displayDownloadScreen(downloadScreenData, firstPaint);
                    firstPaint = false;
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }

                try {
                    // Adjust the sleep duration based on your needs
                    Thread.sleep(500); // Sleep for 500 milliseconds before updating again
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            try {
                CLIDisplay.displayDownloadScreen(downloadScreenData, false);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }

            while (true) {
                if (appStateManager.getDownloadScreenData().getTotalFileProgress() == 100) {
                    printAtPosition("File split successfully into " + appStateManager.getNumberOfChunks() + " chunks.", 30);
                    FileMerger.mergeChunks();
                    break;
                }
            }
        };

        Thread run = new Thread(screenThread);
        run.setDaemon(false);
        return run;
    }
}
