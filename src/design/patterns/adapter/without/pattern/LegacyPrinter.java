package src.design.patterns.adapter.without.pattern;

public class LegacyPrinter {
    // stara klasa, która posiada metodę, której nie chcemy zmieniać
    public void printText(String text) {
        System.out.println(text);
        System.out.println("Using LegacyPrinter");
    }
}
