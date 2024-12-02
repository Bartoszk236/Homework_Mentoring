package src.string.operation.task2;

public class Main {
    public static void main(String[] args) {
        String s = "bartosz";
        System.out.println(toUpperCase(s));
    }

    public static String toUpperCase(String input) {
        char[] chars = input.toCharArray();
        char[] newChars = new char[chars.length];
        for (int i = 0; i < chars.length; i++) {
            newChars[i] = Character.toUpperCase(chars[i]);
        }
        return new String(newChars);
    }
}