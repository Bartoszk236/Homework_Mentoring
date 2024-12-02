package src.staticand.unstatic.task27;

public class Example {
    static String host;
    static int port;
    static boolean debug;

    static {
        port = 8080;
        host = "127.0.0.1";
        debug = false;
        System.out.println("Successful initialization static variables");
    }
}