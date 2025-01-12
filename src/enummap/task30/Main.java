package src.enummap.task30;

import java.util.EnumMap;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        enum ProductCategory {
            GARDEN, KITCHEN, LIVING_ROOM, BATHROOM, GARAGE
        }
        EnumMap<ProductCategory, String> reports = new EnumMap<>(ProductCategory.class);
        reports.put(ProductCategory.GARDEN, "Better than kitchen");
        reports.put(ProductCategory.KITCHEN, "Better than last month");
        reports.put(ProductCategory.LIVING_ROOM, "The best all time");
        reports.put(ProductCategory.BATHROOM, "Worst all time");
        reports.put(ProductCategory.GARAGE, "Better than bathroom");
        Iterator<ProductCategory> iterator = reports.keySet().iterator();
        while (iterator.hasNext()) {
            ProductCategory category = iterator.next();
            String report = reports.get(category);
            System.out.println(category.name() + ": " + report);
        }
    }
}