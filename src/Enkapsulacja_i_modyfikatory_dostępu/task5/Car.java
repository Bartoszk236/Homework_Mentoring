package src.Enkapsulacja_i_modyfikatory_dostępu.task5;

import java.time.LocalDate;

public class Car {
    private String model;
    private int year;
    private int mileage = 0;

    public Car increaseMileage(int mileage) {
        if (mileage < 0) throw new IllegalArgumentException("mileage cannot be negative");
        this.mileage = mileage;
        return this;
    }

    public Car setYear(int year) {
        if (year > LocalDate.now().getYear()) throw new IllegalArgumentException("year cannot be greater than now year");
        this.year = year;
        return this;
    }
}
