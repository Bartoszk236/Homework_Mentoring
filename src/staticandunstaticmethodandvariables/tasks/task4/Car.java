package src.staticandunstaticmethodandvariables.tasks.task4;

public class Car {
    public static int carCount = 0;
    public String model;

    public Car(String model){
        this.model = model;
        increment();
    }

    public static void increment(){
        carCount++;
    }
}