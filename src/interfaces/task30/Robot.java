package src.interfaces.task30;

public class Robot implements Movable {
    @Override
    public void move() {
        System.out.println("The robot moved two meters on the ground");
    }
}