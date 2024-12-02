package src.string.operation.task5;

public class Main {
    public static void main(String[] args) {
        String str = "a b c";
        System.out.println(replaceSpaceToUnderscore(str));
    }

    public static String replaceSpaceToUnderscore(String input) {
        return input.replaceAll(" ", "_");
    }
}