package src.interfaces.task30;

public class Drone implements Movable {
    @Override
    public void move() {
        System.out.println("The drone moved two meters in the air");
    }
}