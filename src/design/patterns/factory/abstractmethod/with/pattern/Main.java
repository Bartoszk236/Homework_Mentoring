package src.design.patterns.factory.abstractmethod.with.pattern;

import src.design.patterns.factory.abstractmethod.with.pattern.factories.LinuxFactory;
import src.design.patterns.factory.abstractmethod.with.pattern.factories.MacOSFactory;
import src.design.patterns.factory.abstractmethod.with.pattern.factories.WindowsFactory;
import src.design.patterns.factory.abstractmethod.with.pattern.functionalities.Button;
import src.design.patterns.factory.abstractmethod.with.pattern.functionalities.DialogWindow;
import src.design.patterns.factory.abstractmethod.with.pattern.functionalities.TextField;

public class Main {
    public static GUIFactory factory;

    public static void main(String[] args) {
        String osName = "Linux";
        switch (osName){
            case "Linux": factory = new LinuxFactory(); break;
            case "Windows": factory = new WindowsFactory(); break;
            case "Mac OS X": factory = new MacOSFactory(); break;
        }
        Button button = factory.createButton();
        TextField textField = factory.createTextField();
        DialogWindow dialogWindow = factory.createDialogWindow();

        System.out.println("OS Name: " + osName);
        button.display();
        textField.display();
        dialogWindow.display();
    }
}
