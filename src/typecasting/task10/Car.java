package src.typecasting.task10;

public class Car extends Vehicle {
    private int door;

    public Car(String name, int door) {
        super(name);
        this.door = door;
    }

    public int getDoor() {
        return door;
    }

    public void openDoor() {
        System.out.println("Opening door");
    }
}