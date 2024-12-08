package src.classes.and.abstracts.methods.task1;

abstract public class Shape {
    private String color;

    public Shape(String color) {
        this.color = color;
    }

    abstract void calculatePerimeter();
}