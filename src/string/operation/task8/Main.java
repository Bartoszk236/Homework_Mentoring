package src.string.operation.task8;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String text = "Jestem Bartosz i mam 21 lat";
        int sizeOfPart = 5;
        splitText(text, sizeOfPart).forEach(System.out::println);
    }

    public static List<String> splitText(String text, int sizeOfPart) {
        List<String> results = new ArrayList<>();
        for (int i = 0; i < text.length();) {
            results.add(text.substring(i, Math.min(i + sizeOfPart, text.length())));
            i += sizeOfPart;
        }
        return results;
    }
}