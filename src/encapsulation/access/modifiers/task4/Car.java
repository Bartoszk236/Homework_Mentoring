package src.encapsulation.access.modifiers.task4;

public class Car {
    private String model;
    private int year;
    private int mileage = 0;

    public Car increaseMileage(int mileage) {
        if (mileage < 0) throw new IllegalArgumentException("mileage cannot be negative");
        this.mileage = mileage;
        return this;
    }
}