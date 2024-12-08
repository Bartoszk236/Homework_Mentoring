package src.practical.application.of.abstraction.task24;

public class Monkey extends Animal {
    public Monkey(String foodType, String name) {
        super(foodType, name);
    }

    @Override
    public void eat() {
        System.out.println("Monkey want food. Don't worry " + getName() + " is omnivorous");
    }

    @Override
    public void makeSound() {
        System.out.println("Monkey makes sound");
    }
}
