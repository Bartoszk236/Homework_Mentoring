package src.staticandunstaticmethodandvariables.tasks.task13;

public class Logger {
    private static int logCount = 0;

    public static void log(String message) {
        logCount++;
        System.out.println(message);
    }
    public static int getLogCount() {
        return logCount;
    }
}