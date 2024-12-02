package src.string.operation.task3;

public class Main {
    public static void main(String[] args) {
        String s = "Bartosz 1646,09";
        System.out.println(countOfNumbers(s));
    }

    public static int countOfNumbers(String input) {
        int count = 0;
        char[] chars = input.toCharArray();
        for (char aChar : chars) {
            if (Character.isDigit(aChar)) {
                count++;
            }
        }
        return count;
    }
}