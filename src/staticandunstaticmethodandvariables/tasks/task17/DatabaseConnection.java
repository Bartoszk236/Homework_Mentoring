package src.staticandunstaticmethodandvariables.tasks.task17;

public class DatabaseConnection {
    private static boolean isConnected = false;

    public static void connect() {
        isConnected = true;
    }
    public static boolean getIsConnected() {
        return isConnected;
    }
}