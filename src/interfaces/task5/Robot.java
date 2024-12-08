package src.interfaces.task5;

public class Robot implements Workable {
    @Override
    public void endWork() {
        System.out.println("Robot has ended.");
    }

    @Override
    public void startWork() {
        System.out.println("Robot has started.");
    }
}