package src.design.patterns.factory.abstractmethod.without.pattern.platforms;

public class Linux extends OSType {
    @Override
    public void showButton() {
        System.out.println("Linux button");
    }

    @Override
    public void showTextField() {
        System.out.println("Linux text field");
    }

    @Override
    public void showDialogWindow() {
        System.out.println("Linux dialog window");
    }
}
