package src.design.patterns.decorator;

public class MushroomDecorator extends PizzaDecorator {

    public MushroomDecorator(Pizza pizza) {
        super(pizza);
    }

    public String getDescription() {
        return decoratedPizza.getDescription() + ", pieczarki";
    }

    public Double getPrice() {
        return decoratedPizza.getPrice() + 2.5;
    }
}
