package src.instanceofmethods.task78;

public class Main {
    public static void main(String[] args) {
        Animal animal1 = new Dog();
        Animal animal2 = new Cat();

        Animal[] animals = {animal1, animal2};

        for (Animal animal : animals) {
            if (animal instanceof Dog) {
                System.out.println(animal + " is a Dog");
            }
            if (animal instanceof Cat) {
                System.out.println(animal + " is a Cat");
            }
        }
        //task 8
        processAnimal(animal2);
    }
    //task 8
    public static void processAnimal(Animal animal) {
        if (animal instanceof Dog) {
            System.out.println("Your animal is from wolf");
        }
        if (animal instanceof Cat) {
            System.out.println("Your animal is from tiger");
        }
    }
}