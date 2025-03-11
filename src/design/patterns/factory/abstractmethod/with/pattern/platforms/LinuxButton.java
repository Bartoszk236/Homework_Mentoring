package src.design.patterns.factory.abstractmethod.with.pattern.platforms;

import src.design.patterns.factory.abstractmethod.with.pattern.functionalities.Button;

public class LinuxButton implements Button {
    @Override
    public void display() {
        System.out.println("Linux Button");
    }
}
