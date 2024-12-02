package src.string.operation.task16;

public class Main {
    public static void main(String[] args) {
        System.out.println(addChar("Bartosz", 'G'));
    }

    public static String addChar(String word, char addChar) {
        StringBuffer sb = new StringBuffer();
        sb.append(word).append(addChar).reverse().append(addChar).reverse();
        return sb.toString();
    }
}