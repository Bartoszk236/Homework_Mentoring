package src.inheritance.task20;

public class Truck extends Vehicle {
    private final int capacity;

    public Truck(String name, int numberOfWheels, int capacity) {
        super(name, numberOfWheels);
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void newOrder() {
        System.out.println(getName() + " have new order");
    }
}