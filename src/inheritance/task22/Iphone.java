package src.inheritance.task22;

public class Iphone extends Device {
    private String model;

    public Iphone(String producer, String model) {
        super(producer);
        // użycie super powoduje odwołanie się do konstruktora klasy nadrzędnej
        this.model = model;
    }

    public void whoIsProducer() {
        System.out.println(super.getProducer());
        //odwołanie się do metody klasy nadrzędnej
    }
}