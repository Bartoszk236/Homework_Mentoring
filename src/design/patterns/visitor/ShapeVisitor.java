package src.design.patterns.visitor;

public interface ShapeVisitor {
    void visit(Rectangle rectangle);
    void visit(Square square);
    void visit(Circle circle);
}
