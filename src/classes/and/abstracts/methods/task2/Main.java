package src.classes.and.abstracts.methods.task2;

public class Main {
    public static void main(String[] args) {
        Appliance washingMachine = new WashingMachine();
        Appliance refrigerator = new Refrigerator();
        washingMachine.turnOn();
        refrigerator.turnOn();
    }
}