package src.string.operation.task15;

public class Main {
    public static void main(String[] args) {
        System.out.println(reverse("Bartosz"));
    }

    public static String reverse(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        return sb.reverse().toString();
    }
}