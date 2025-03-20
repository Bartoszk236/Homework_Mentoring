package src.design.patterns.decorator;

public class DefaultPizza implements Pizza {

    @Override
    public String getDescription() {
        return "Pizza: sos pomidorowy, ser";
    }

    @Override
    public Double getPrice() {
        return 10.5;
    }
}
