package src.inheritance.task18;

public class Bird extends Animal {
    private double wingSize;

    public Bird(String name, double wingSize) {
        super(name);
        this.wingSize = wingSize;
    }

    public double getWingSize() {
        return wingSize;
    }

    public Bird setWingSize(double wingSize) {
        this.wingSize = wingSize;
        return this;
    }
}