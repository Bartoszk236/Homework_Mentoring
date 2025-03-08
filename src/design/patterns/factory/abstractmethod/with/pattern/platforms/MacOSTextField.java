package src.design.patterns.factory.abstractmethod.with.pattern.platforms;

import src.design.patterns.factory.abstractmethod.with.pattern.functionalities.TextField;

public class MacOSTextField implements TextField {
    @Override
    public void display() {
        System.out.println("Mac OS X Text Field");
    }
}
