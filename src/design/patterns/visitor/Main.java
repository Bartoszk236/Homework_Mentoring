package src.design.patterns.visitor;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Shape> shapes = List.of(
                new Circle(5),
                new Square(6),
                new Rectangle(7, 4));

        ShapeVisitor calculate = new CalculateAreaVisitor();

        shapes.forEach(shape -> shape.accept(calculate));
    }
}
