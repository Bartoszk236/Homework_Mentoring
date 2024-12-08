package src.practical.application.of.abstraction.task24;

public class Lion extends Animal {
    public Lion(String foodType, String name) {
        super(foodType, name);
    }

    @Override
    public void eat() {
        System.out.println("Lion want food. Remember " + getName() + " eat " + getFoodType());
    }

    @Override
    public void makeSound() {
        System.out.println("Lion makes sound");
    }
}