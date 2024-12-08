package src.inheritance.hierarchy.task27;

public class Car extends Vehicle {
    private int maxSpeed;

    public Car(String name, int maxSpeed) {
        super(name);
        this.maxSpeed = maxSpeed;
    }

    @Override
    void move() {
        System.out.println("Car moving on the road. It have max speed: " + maxSpeed);
    }
}