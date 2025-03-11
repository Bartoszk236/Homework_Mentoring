package src.design.patterns.factory.abstractmethod.with.pattern.factories;

import src.design.patterns.factory.abstractmethod.with.pattern.GUIFactory;
import src.design.patterns.factory.abstractmethod.with.pattern.functionalities.Button;
import src.design.patterns.factory.abstractmethod.with.pattern.functionalities.DialogWindow;
import src.design.patterns.factory.abstractmethod.with.pattern.functionalities.TextField;
import src.design.patterns.factory.abstractmethod.with.pattern.platforms.MacOSButton;
import src.design.patterns.factory.abstractmethod.with.pattern.platforms.MacOSDialogWindow;
import src.design.patterns.factory.abstractmethod.with.pattern.platforms.MacOSTextField;
import src.design.patterns.factory.abstractmethod.with.pattern.platforms.WindowsDialogWindow;

public class MacOSFactory implements GUIFactory {
    @Override
    public Button createButton() {
        return new MacOSButton();
    }

    @Override
    public TextField createTextField() {
        return new MacOSTextField();
    }

    @Override
    public DialogWindow createDialogWindow() {
        return new MacOSDialogWindow();
    }
}
