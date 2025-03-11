package src.design.patterns.adapter.with.pattern;

public class Main {
    public static void main(String[] args) {
        Display display = new DisplayAdapter(new LegacyPrinter());
        display.displayText("Hello World");
    }
}
