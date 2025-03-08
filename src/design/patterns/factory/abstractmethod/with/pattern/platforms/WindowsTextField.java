package src.design.patterns.factory.abstractmethod.with.pattern.platforms;

import src.design.patterns.factory.abstractmethod.with.pattern.functionalities.TextField;

public class WindowsTextField implements TextField {
    @Override
    public void display() {
        System.out.println("Windows Text Field");
    }
}
