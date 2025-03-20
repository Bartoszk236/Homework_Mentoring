package src.design.patterns.decorator;

public class PaprikaDecorator extends PizzaDecorator {

    public PaprikaDecorator(Pizza pizza) {
        super(pizza);
    }

    public String getDescription() {
        return decoratedPizza.getDescription() + ", papryka";
    }

    public Double getPrice() {
        return decoratedPizza.getPrice() + 2.5;
    }
}
