package src.string.operation.task6;

public class Main {
    public static void main(String[] args) {
        String text = "Witam jestem Bartosz mam 21 lat. Uczę się javy i jestem zadowolony";
        String keyWord = "jestem";
        System.out.println(numberOfWordsInText(text, keyWord));
    }

    public static int numberOfWordsInText(String text, String keyWord) {
        int count = 0;
        String[] splitText = text.split(" ");
        for (String s : splitText) {
            if (s.contains(keyWord)) {
                count++;
            }
        }
        return count;
    }
}