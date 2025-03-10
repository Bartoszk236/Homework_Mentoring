package src.design.patterns.adapter.without.pattern;

public class Main {
    public static void main(String[] args) {
        LegacyPrinter printer = new LegacyPrinter();
        Display display = new Display() {
            @Override
            public void displayText(String text) {
                printer.printText(text);
            }
        };
        display.displayText("Hello World");
    }
}
