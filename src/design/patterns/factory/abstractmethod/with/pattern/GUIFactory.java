package src.design.patterns.factory.abstractmethod.with.pattern;

import src.design.patterns.factory.abstractmethod.with.pattern.functionalities.Button;
import src.design.patterns.factory.abstractmethod.with.pattern.functionalities.DialogWindow;
import src.design.patterns.factory.abstractmethod.with.pattern.functionalities.TextField;

public interface GUIFactory {
    Button createButton();
    TextField createTextField();
    DialogWindow createDialogWindow();
}
