package src.Rozszerzone_zadania_z_relacji.task18;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private List<Supplier> suppliers = new ArrayList<>();

    public Product addSupplier(Supplier supplier) {
        suppliers.add(supplier);
        return this;
    }
}
