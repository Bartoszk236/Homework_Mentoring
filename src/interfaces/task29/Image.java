package src.interfaces.task29;

public class Image implements Resizable {
    private String name;

    public Image(String name) {
        this.name = name;
    }

    @Override
    public void resizeDown() {
        System.out.println("Image has been reduced");
    }

    @Override
    public void resizeUp() {
        System.out.println("Image has been enlarged");
    }
}