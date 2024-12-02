package src.inheritance.task20;

public class Car extends Vehicle {
    private final int numberOfDoors;

    public Car(String name, int numberOfWheels, int numberOfDoors) {
        super(name, numberOfWheels);
        this.numberOfDoors = numberOfDoors;
    }

    public int getNumberOfDoors() {
        return numberOfDoors;
    }

    public void oilChange() {
        System.out.println("Oil changed");
    }
}