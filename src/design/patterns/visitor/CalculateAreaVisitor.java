package src.design.patterns.visitor;

public class CalculateAreaVisitor implements ShapeVisitor {
    @Override
    public void visit(Rectangle rectangle) {
        System.out.println("Pole to: " + rectangle.getHeight() * rectangle.getWidth());
    }

    @Override
    public void visit(Square square) {
        System.out.println("Pole to: " + Math.pow(square.getWidth(), 2));
    }

    @Override
    public void visit(Circle circle) {
        System.out.println("Pole to: " + Math.pow(circle.getRadius(), 2) * Math.PI);
    }
}
