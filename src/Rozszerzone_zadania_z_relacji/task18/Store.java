package src.Rozszerzone_zadania_z_relacji.task18;

import java.util.ArrayList;
import java.util.List;

public class Store {
    private List<Product> products = new ArrayList<>();

    public Store addProduct(Product product) {
        products.add(product);
        return this;
    }
}
