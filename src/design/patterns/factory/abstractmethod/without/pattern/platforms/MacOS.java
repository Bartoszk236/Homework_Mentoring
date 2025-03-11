package src.design.patterns.factory.abstractmethod.without.pattern.platforms;

public class MacOS extends OSType {
    @Override
    public void showButton() {
        System.out.println("MacOS button");
    }

    @Override
    public void showTextField() {
        System.out.println("MacOS text field");
    }

    @Override
    public void showDialogWindow() {
        System.out.println("MacOS dialog window");
    }
}
