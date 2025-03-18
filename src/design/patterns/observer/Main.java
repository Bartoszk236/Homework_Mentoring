package src.design.patterns.observer;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        PriceUpChannel priceUpChannel = new PriceUpChannel();
        User user1 = new User();
        User user2 = new User();

        //jednorazowy alert o przekroczeniu ceny
        priceUpChannel.registerObserver(user1, new BigDecimal("200"));
        priceUpChannel.registerObserver(user2, new BigDecimal("250"));

        priceUpChannel.notifyObservers();
        //brak alertów

        priceUpChannel.setPrice(new BigDecimal("201"));
        priceUpChannel.notifyObservers();
        //alert + usunięcie z mapy obserwatorów

        priceUpChannel.setPrice(new BigDecimal("202"));
        priceUpChannel.notifyObservers();
        //brak alertów

        priceUpChannel.setPrice(BigDecimal.ZERO);
        priceUpChannel.registerObserver(user1, new BigDecimal("200"));
        priceUpChannel.setPrice(new BigDecimal("301"));
        priceUpChannel.notifyObservers();
        //dwa alerty dla obydwóch subskrybentów

        priceUpChannel.setPrice(BigDecimal.ZERO);
        priceUpChannel.registerObserver(user1, new BigDecimal("200"));
        priceUpChannel.removeObserver(user1);
        priceUpChannel.setPrice(new BigDecimal("201"));
        priceUpChannel.notifyObservers();
        //możliwość usunięcia alertu przed jego wyzwoleniem
    }
}
