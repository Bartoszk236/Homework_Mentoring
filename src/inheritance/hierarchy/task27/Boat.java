package src.inheritance.hierarchy.task27;

public class Boat extends Vehicle {
    private double length;

    public Boat(String name, double length) {
        super(name);
        this.length = length;
    }

    @Override
    void move() {
        System.out.println("Boat is moving on the water");
    }
}