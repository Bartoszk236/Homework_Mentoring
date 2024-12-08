package src.typecasting.task10;

public class SportsCar extends Car {
    private int maxSpeed;

    public SportsCar(String name, int door, int maxSpeed) {
        super(name, door);
        this.maxSpeed = maxSpeed;
    }

    public void displayMaxSpeed(){
        System.out.println(maxSpeed);
    }
}