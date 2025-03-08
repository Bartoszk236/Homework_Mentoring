package src.design.patterns.factory.abstractmethod.with.pattern.factories;

import src.design.patterns.factory.abstractmethod.with.pattern.GUIFactory;
import src.design.patterns.factory.abstractmethod.with.pattern.functionalities.Button;
import src.design.patterns.factory.abstractmethod.with.pattern.functionalities.DialogWindow;
import src.design.patterns.factory.abstractmethod.with.pattern.functionalities.TextField;
import src.design.patterns.factory.abstractmethod.with.pattern.platforms.LinuxButton;
import src.design.patterns.factory.abstractmethod.with.pattern.platforms.LinuxDialogWindow;
import src.design.patterns.factory.abstractmethod.with.pattern.platforms.LinuxTextField;

public class LinuxFactory implements GUIFactory {
    @Override
    public Button createButton() {
        return new LinuxButton();
    }

    @Override
    public TextField createTextField() {
        return new LinuxTextField();
    }

    @Override
    public DialogWindow createDialogWindow() {
        return new LinuxDialogWindow();
    }
}
