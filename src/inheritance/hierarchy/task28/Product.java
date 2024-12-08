package src.inheritance.hierarchy.task28;

import java.math.BigDecimal;

abstract public class Product {
    private int id;
    private String name;
    private BigDecimal price;

    public Product(int id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    abstract void buy();
}