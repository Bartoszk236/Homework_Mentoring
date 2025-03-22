package src.design.patterns.visitor;

public class Square implements Shape {
    private final double width;

    public Square(double width) {
        this.width = width;
    }

    public double getWidth() {
        return width;
    }

    @Override
    public void accept(ShapeVisitor visitor) {
        visitor.visit(this);
    }
}
