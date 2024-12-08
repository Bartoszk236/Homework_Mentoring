package src.anonymous.classes.task19;

public class Person {
    private String name;

    public Person(String name) {
        this.name = name;
        sayHello();
    }

    public void sayHello() {
        System.out.println("Hello " + name + "! Your initialization successful");
    }

    private interface Greeting {
        void sayHello();
    }
}