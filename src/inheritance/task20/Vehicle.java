package src.inheritance.task20;

public class Vehicle {
    private int numberOfWheels;
    private String name;

    public Vehicle(String name, int numberOfWheels) {
        this.name = name;
        this.numberOfWheels = numberOfWheels;
    }

    public String getName() {
        return name;
    }

    public Vehicle setName(String name) {
        this.name = name;
        return this;
    }

    public int getNumberOfWheels() {
        return numberOfWheels;
    }

    public Vehicle setNumberOfWheels(int numberOfWheels) {
        this.numberOfWheels = numberOfWheels;
        return this;
    }

    public void start() {
        System.out.println(getName() + " started");
    }

    public void stop() {
        System.out.println(getName() + " stopped");
    }
}