package src.polymorphism.task13;

public class TextPrinter extends Printer {
    private String textColor;

    public TextPrinter(String textColor) {
        this.textColor = textColor;
    }

    @Override
    public void print() {
        System.out.println("Starting printing text on color: " + textColor);
    }
}