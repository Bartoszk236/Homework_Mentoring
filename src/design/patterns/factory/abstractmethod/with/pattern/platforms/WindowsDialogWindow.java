package src.design.patterns.factory.abstractmethod.with.pattern.platforms;

import src.design.patterns.factory.abstractmethod.with.pattern.functionalities.DialogWindow;

public class WindowsDialogWindow implements DialogWindow {
    @Override
    public void display() {
        System.out.println("Windows Dialog Window");
    }
}
