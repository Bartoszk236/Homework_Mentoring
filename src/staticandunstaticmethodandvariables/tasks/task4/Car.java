package src.staticandunstaticmethodandvariables.tasks.task4;

public class Car {
    public static int carCount = 0;
    public String model;
    public static void increment(){
        carCount++;
    }

    public Car(String model){
        this.model = model;
        increment();
    }
}