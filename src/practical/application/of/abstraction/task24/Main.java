package src.practical.application.of.abstraction.task24;

public class Main {
    public static void main(String[] args) {
        Animal animal1 = new Elephant("plants", "Maniek", 8500);
        Animal animal2 = new Lion("meat", "Jakub");
        Animal animal3 = new Monkey("omnivorous", "Patryk");

        animal1.eat();
        animal1.makeSound();

        animal2.eat();
        animal2.makeSound();

        animal3.eat();
        animal1.makeSound();
    }
}