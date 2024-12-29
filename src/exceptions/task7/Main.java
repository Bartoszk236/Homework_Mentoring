package src.exceptions.task7;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println(calculateArea(3, 1));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static double calculateArea(double width, double height) {
        if (width <= 0 || height <= 0) throw new IllegalArgumentException("width and height must be greater than 0");
        return width * height;
    }
}