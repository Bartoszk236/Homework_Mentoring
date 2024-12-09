package src.practical.application.of.abstraction.task24;

abstract public class Animal {
    private String name;
    private String foodType;

    public Animal(String foodType, String name) {
        this.foodType = foodType;
        this.name = name;
    }

    public String getFoodType() {
        return foodType;
    }

    public String getName() {
        return name;
    }

    abstract public void eat();
    abstract public void makeSound();
}
