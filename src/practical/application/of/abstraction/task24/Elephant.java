package src.practical.application.of.abstraction.task24;

public class Elephant extends Animal {
    private double weight;

    public Elephant(String foodType, String name, double weight) {
        super(foodType, name);
        this.weight = weight;
    }

    @Override
    public void eat() {
        System.out.println("Elephant want food. Remember " + getName() + " eat " + getFoodType());
    }

    @Override
    public void makeSound() {
        System.out.println("Elephant sound made.");
    }
}