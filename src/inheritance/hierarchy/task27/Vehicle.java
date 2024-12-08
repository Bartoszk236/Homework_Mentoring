package src.inheritance.hierarchy.task27;

abstract public class Vehicle {
    private String name;

    public Vehicle(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    abstract void move();
}