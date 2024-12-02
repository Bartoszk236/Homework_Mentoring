package src.inheritance.task20;

public class Bike extends Vehicle {
    private String color;

    public Bike(String name, int numberOfWheels, String color) {
        super(name, numberOfWheels);
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public Bike setColor(String color) {
        this.color = color;
        return this;
    }

    public void newMethod(){
        System.out.println("bike method");
    }
}