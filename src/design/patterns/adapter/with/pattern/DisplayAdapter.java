package src.design.patterns.adapter.with.pattern;

public class DisplayAdapter implements Display {
    // klasa adapter, która ma umożliwić kompatybilność
    LegacyPrinter printer;

    public DisplayAdapter(LegacyPrinter printer) {
        this.printer = printer;
    }

    // nadpisana metoda, która umożliwia skorzystanie ze starej metody bez potrzeby jej modyfikacji
    @Override
    public void displayText(String text) {
        printer.printText(text);
    }
}
