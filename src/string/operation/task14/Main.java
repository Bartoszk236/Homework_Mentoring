package src.string.operation.task14;

public class Main {
    public static void main(String[] args) {
        isEqual();
    }

    public static void isEqual() {
        String normalString = "Bartosz";
        String newString = new String("Bartosz");
        System.out.println("Equals method: " + normalString.equals(newString));
        System.out.println("The same memory address: " + (normalString == newString));
    }
}