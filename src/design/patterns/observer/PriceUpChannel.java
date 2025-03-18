package src.design.patterns.observer;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PriceUpChannel implements PriceAlert {
    private BigDecimal price = BigDecimal.ZERO;
    private Map<Observer, BigDecimal> observers = new HashMap<>();

    @Override
    public void notifyObservers() {
        Iterator<Map.Entry<Observer, BigDecimal>> iterator = observers.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Observer, BigDecimal> entry = iterator.next();
            Observer observer = entry.getKey();
            BigDecimal value = entry.getValue();
            if (value.compareTo(this.price) < 0) {
                observer.update("Cena przekroczyła: " + value);
                iterator.remove();
            }
        }
    }

    @Override
    public void registerObserver(Observer observer, BigDecimal price) {
        observers.put(observer, price);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public PriceUpChannel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
}
