package src.string.operation.task4;

public class Main {
    public static void main(String[] args) {
        String str = "abc";
        System.out.println(isPalindrome(str));
    }

    public static boolean isPalindrome(String input) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(input);
        String reverse = stringBuilder.reverse().toString();
        return input.equals(reverse);
    }
}