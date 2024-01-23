package app;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppLogger {
    private static final Logger logger = Logger.getLogger(AppLogger.class.getName());

    // ANSI escape codes for color
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_YELLOW = "\u001B[33m";

    private static String getStackTraceAsString(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

    private static String getCallerInfo() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        if (stackTraceElements.length > 3) { // Adjust the index based on your usage
            StackTraceElement caller = stackTraceElements[3];
            return caller.getFileName() + ":" + caller.getLineNumber();
        }
        return "Unknown";
    }

    public static void error(String str) {
        System.out.println(ANSI_RED + "[ERROR] " + str + ANSI_RESET);
        logger.logp(Level.SEVERE, AppLogger.class.getName(), getCallerInfo(), str);
    }

    public static void info(String str) {
        System.out.println(ANSI_RESET + "[INFO] " + str + ANSI_RESET);
        logger.logp(Level.INFO, AppLogger.class.getName(), getCallerInfo(), str + "\n");
    }

    public static void warning(String str) {
        System.out.println(ANSI_YELLOW + "[WARNING] " + str + ANSI_RESET);
        logger.logp(Level.WARNING, AppLogger.class.getName(), getCallerInfo(), str);
    }

    public static void debug(String str) {
        System.out.println("________________________________DEBUG__________________________________________");
        info(str);
        System.out.println("_______________________________________________________________________________");
    }
}
