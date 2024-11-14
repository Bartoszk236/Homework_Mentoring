package src.Enkapsulacja_i_modyfikatory_dostępu.task4;

public class Car {
    private String model;
    private int year;
    private int mileage = 0;

    public Car increaseMileage(int mileage) {
        if (mileage < 0) throw new IllegalArgumentException("mileage cannot be negative");
        this.mileage = mileage;
        return this;
    }
}
