package src.advanced.tasks.relationships.task18;

import java.util.ArrayList;
import java.util.List;

public class Store {
    private List<Product> products = new ArrayList<>();

    public Store addProduct(Product product) {
        products.add(product);
        return this;
    }
}