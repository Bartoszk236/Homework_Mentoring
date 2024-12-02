package src.staticand.unstatic.task26;

public class Base {
    static {
        System.out.println("Base static block");
    }

    {
        System.out.println("Base non-static block");
    }

    public Base() {
        System.out.println("Base constructor");
    }
}