package src.design.patterns.factory.abstractmethod.without.pattern.platforms;

public class Windows extends OSType {
    @Override
    public void showButton() {
        System.out.println("Windows button");
    }

    @Override
    public void showTextField() {
        System.out.println("Windows text field");
    }

    @Override
    public void showDialogWindow() {
        System.out.println("Windows dialog window");
    }
}
