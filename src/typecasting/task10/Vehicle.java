package src.typecasting.task10;

public class Vehicle {
    private String name;

    public Vehicle(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void start(){
        System.out.println("Vehicle starting");
    }
}