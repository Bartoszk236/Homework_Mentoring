package src.typecasting.task10;

public class Main {
    public static void main(String[] args) {
        SportsCar sportsCar = new SportsCar("Ferrari", 2, 355);

        Car car = sportsCar;
        car.openDoor();
        Vehicle vehicle = car;
        vehicle.start();

        if (vehicle instanceof Car) {
            ((Car) vehicle).openDoor();
        }
        if (vehicle instanceof SportsCar) {
            ((SportsCar) vehicle).displayMaxSpeed();
        }
    }
}