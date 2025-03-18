package src.design.patterns.decorator;

public class Main {
    public static void main(String[] args) {
        Pizza pizza = new DefaultPizza();
        System.out.println(pizza.getDescription() + " Cena: " + pizza.getPrice());

        pizza = new SalamiDecorator(pizza);
        System.out.println(pizza.getDescription() + " Cena: " + pizza.getPrice());

        pizza = new PaprikaDecorator(pizza);
        System.out.println(pizza.getDescription() + " Cena: " + pizza.getPrice());

        pizza = new MushroomDecorator(pizza);
        System.out.println(pizza.getDescription() + " Cena: " + pizza.getPrice());
    }
}
