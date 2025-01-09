package src.exceptions.task14;

public class ServerOverloadException extends RuntimeException {
    public ServerOverloadException() {
        super("Server is overload, please try again later.");
    }
}
