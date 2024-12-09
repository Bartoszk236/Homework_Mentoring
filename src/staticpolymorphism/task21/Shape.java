package src.staticpolymorphism.task21;

public class Shape {
    public static void draw(Circle circle) {
        System.out.println("Drawing a Circle");
    }

    public static void draw(Square square) {
        System.out.println("Drawing a Square");
    }

    public static void draw(Triangle triangle) {
        System.out.println("Drawing a Triangle");
    }
}