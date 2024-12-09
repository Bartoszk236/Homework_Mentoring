package src.dynamic.polymorphism.task23;

public class Main {
    public static void main(String[] args) {
        Vehicle vehicle1 = new Bicycle();
        Vehicle vehicle2 = new Car(40.0, 7.5);

        vehicle1.move();
        vehicle2.move();
    }
}