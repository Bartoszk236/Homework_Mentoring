package src.polymorphism.task13;

public class ImagePrinter extends Printer {
    private String imagePath;

    public ImagePrinter(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public void print() {
        System.out.println("Starting printing photo from: " + imagePath);
    }
}