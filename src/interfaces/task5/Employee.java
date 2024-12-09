package src.interfaces.task5;

public class Employee implements Workable {
    @Override
    public void endWork() {
        System.out.println("Employee has been ended");
    }

    @Override
    public void startWork() {
        System.out.println("Employee has been started");
    }
}
