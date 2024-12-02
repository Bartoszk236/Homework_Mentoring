package src.string.operation.task1;

public class Main {
    public static void main(String[] args) {
        String str = "abc";
        System.out.println(onlyLetters(str));
    }

    public static boolean onlyLetters(String input) {
        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (!Character.isLetter(chars[i])) {
                return false;
            }
        }
        return true;
    }
}