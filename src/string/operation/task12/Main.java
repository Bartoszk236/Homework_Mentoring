package src.string.operation.task12;

public class Main {
    public static void main(String[] args) {
        String str1 = "abc";
        String str2 = "abc";
        String str3 = new String("abc");
        System.out.println(theSameAddressInMemory(str1, str2));
        //true ponieważ jest ten sam adres w string pool
        System.out.println(theSameAddressInMemory(str1, str3));
        //false poniważ str3 sworzył nowy obiekt z nową referencją
    }

    public static boolean theSameAddressInMemory(String x, String y) {
        return x == y;
    }
}