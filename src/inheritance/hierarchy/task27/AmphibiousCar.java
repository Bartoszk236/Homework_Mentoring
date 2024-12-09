package src.inheritance.hierarchy.task27;

public class AmphibiousCar extends Vehicle implements Flyable {
    private String fuelType;
    private Car car;
    private Boat boat;

    public AmphibiousCar(String name, String fuelType) {
        super(name);
        this.fuelType = fuelType;
    }

    @Override
    public void fly() {
        System.out.println(getName() + " can fly");
    }

    @Override
    void move() {
        System.out.println(getName() + " can move everywhere");
    }

    void drive() {
        car.move();
    }

    void swim() {
        boat.move();
    }
}