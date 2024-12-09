package src.inheritance.hierarchy.task28;

import java.math.BigDecimal;

public class Ebook extends Product {
    private int duration;

    public Ebook(int id, String name, BigDecimal price, int duration) {
        super(id, name, price);
        this.duration = duration;
    }

    @Override
    void buy() {
        System.out.println("E-book has been download");
    }
}