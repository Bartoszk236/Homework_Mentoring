package src.design.patterns.observer;

import java.math.BigDecimal;

public interface PriceAlert {
    void registerObserver(Observer observer, BigDecimal price);
    void removeObserver(Observer observer);
    void notifyObservers();
}
