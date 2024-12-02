package src.inheritance.task20;

public class Main {
    public static void main(String[] args) {
        Car car = new Car("Mercedes", 4, 2);
        Truck truck = new Truck("Volvo", 18, 20000);
        Bike bike = new Bike("Kross", 2, "Black");

        System.out.println(car.getNumberOfDoors());
        System.out.println(truck.getCapacity());
        System.out.println(bike.getColor());
    }
}