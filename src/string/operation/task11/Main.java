package src.string.operation.task11;

public class Main {
    public static void main(String[] args) {
        String a = "hello";
        String b = "Hello";
        System.out.println(isEqual(a, b));
    }

    public static boolean isEqual(String a, String b) {
        return a.equalsIgnoreCase(b);
    }
}