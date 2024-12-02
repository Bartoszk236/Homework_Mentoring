package src.staticand.unstatic.task26;

public class Derived extends Base {
    static {
        System.out.println("Derived static block");
    }

    {
        System.out.println("Derived non-static block");
    }

    public Derived() {
        System.out.println("Derived constructor");
    }
}