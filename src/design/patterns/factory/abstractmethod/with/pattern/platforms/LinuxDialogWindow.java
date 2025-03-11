package src.design.patterns.factory.abstractmethod.with.pattern.platforms;

import src.design.patterns.factory.abstractmethod.with.pattern.functionalities.DialogWindow;

public class LinuxDialogWindow implements DialogWindow {
    @Override
    public void display() {
        System.out.println("Linux Dialog Window");
    }
}
