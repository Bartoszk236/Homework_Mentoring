package src.inheritance.hierarchy.task28;

import java.math.BigDecimal;

public class PhysicalBook extends Product {
    private int numberOfPages;

    public PhysicalBook(int id, String name, BigDecimal price, int numberOfPages) {
        super(id, name, price);
        this.numberOfPages = numberOfPages;
    }

    @Override
    void buy() {
        System.out.println("Book has been sent to your delivery address");
    }
}