package src.design.patterns.factory.abstractmethod.with.pattern.factories;

import src.design.patterns.factory.abstractmethod.with.pattern.GUIFactory;
import src.design.patterns.factory.abstractmethod.with.pattern.functionalities.Button;
import src.design.patterns.factory.abstractmethod.with.pattern.functionalities.DialogWindow;
import src.design.patterns.factory.abstractmethod.with.pattern.functionalities.TextField;
import src.design.patterns.factory.abstractmethod.with.pattern.platforms.WindowsButton;
import src.design.patterns.factory.abstractmethod.with.pattern.platforms.WindowsDialogWindow;
import src.design.patterns.factory.abstractmethod.with.pattern.platforms.WindowsTextField;

public class WindowsFactory implements GUIFactory {
    @Override
    public Button createButton() {
        return new WindowsButton();
    }

    @Override
    public TextField createTextField() {
        return new WindowsTextField();
    }

    @Override
    public DialogWindow createDialogWindow() {
        return new WindowsDialogWindow();
    }
}
