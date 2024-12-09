package src.interfaces.task29;

public class Text implements Resizable {
    private String text;

    public Text(String text) {
        this.text = text;
    }

    @Override
    public void resizeDown() {
        System.out.println("Text has been reduced");
    }

    @Override
    public void resizeUp() {
        System.out.println("Text has been enlarged");
    }
}