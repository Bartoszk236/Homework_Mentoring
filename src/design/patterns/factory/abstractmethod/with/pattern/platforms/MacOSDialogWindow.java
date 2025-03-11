package src.design.patterns.factory.abstractmethod.with.pattern.platforms;

import src.design.patterns.factory.abstractmethod.with.pattern.functionalities.DialogWindow;

public class MacOSDialogWindow implements DialogWindow {
    @Override
    public void display() {
        System.out.println("Mac OS X Dialog Window");
    }
}
