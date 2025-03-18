package src.design.patterns.decorator;

public class SalamiDecorator extends PizzaDecorator {

    public SalamiDecorator(Pizza pizza) {
        super(pizza);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", salami";
    }

    @Override
    public Double getPrice() {
        return super.getPrice() + 1.5;
    }
}
