package src.inheritance.task18;

public class Mammal extends Animal {
    private double lengthOfHair;

    public Mammal(String name, double lengthOfHair) {
        super(name);
        this.lengthOfHair = lengthOfHair;
    }

    public double getLengthOfHair() {
        return lengthOfHair;
    }

    public Mammal setLengthOfHair(double lengthOfHair) {
        this.lengthOfHair = lengthOfHair;
        return this;
    }
}