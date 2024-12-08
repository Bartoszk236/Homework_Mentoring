package src.dynamic.polymorphism.task23;

public class Car extends Vehicle {
    private double fuelQuantity;
    private double fuelConsumption;

    public Car(double fuelQuantity, double fuelConsumption) {
        this.fuelQuantity = fuelQuantity;
        this.fuelConsumption = fuelConsumption;
    }

    @Override
    void move() {
        System.out.println("Car move. Car have fuel for " + (int)((fuelQuantity / fuelConsumption) * 100) + " km");
    }
}