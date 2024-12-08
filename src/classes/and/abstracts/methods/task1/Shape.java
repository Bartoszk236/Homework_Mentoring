package src.classes.and.abstracts.methods.task1;

public abstract class Shape {
    private String color;

    public Shape(String color) {
        this.color = color;
    }

    abstract void calculatePerimeter();
}