package src.exceptions.task8;

public class Main {
    public static void main(String[] args) {
        try {
            printRectangle(6, 4);
        } catch (ArithmeticException e) {
            System.out.println("ArithmeticException: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("IllegalArgumentException: " + e.getMessage());
        }
        //kolejność bloków catch nie ma znaczenia poniważ pierwszy zrzucony wyjątek nie pozwala dokończyć bloku try.
        //mimo że wartości wskazują na obecność obydwóch wyjątków blok catch numer 2 zadziała pierwszy ponieważ ten wyjątek zostanie zrzucowy pierwszy
    }

    public static void printRectangle(int width, int height) {
        if (width <= 0 || height <= 0) throw new IllegalArgumentException("every value must be greater than 0");
        if (width == height) throw new ArithmeticException("this values print square");
        for (int i = 0; i < width; i++) {
            System.out.print("* ");
        }
        System.out.print("\n");
        for (int i = 0; i < height - 2; i++) {
            System.out.print("*");
            for (int j = 0; j < width * 2 - 3; j++) {
                System.out.print(" ");
            }
            System.out.print("*\n");
        }
        for (int i = 0; i < width; i++) {
            System.out.print("* ");
        }
    }
}