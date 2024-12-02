package src.string.operation.task7;

public class Main {
    public static void main(String[] args) {
        String text = "Jestem Bartosz mam 21 lat i tyle";
        System.out.println(longestWordInText(text));
    }

    public static String longestWordInText(String text) {
        int indexOfWinner = 0;
        String[] words = text.split(" ");
        for (int i = 0; i < words.length; i++) {
            if (words[i].length() > words[indexOfWinner].length()) {
                indexOfWinner = i;
            }
        }
        return words[indexOfWinner];
    }
}