package src.staticandunstaticmethodandvariables.tasks.task13;

public class Main {
    public static void main(String[] args) {
        Logger.log("Invalid email");
        Logger.log("Invalid password");
        Logger.log("Reset password request sent to email");
        Logger.log("Password has been reset");
        System.out.println(Logger.getLogCount());
    }
}
