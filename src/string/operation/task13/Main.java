package src.string.operation.task13;

public class Main {
    public static void main(String[] args) {
        String str1 = "abc";
        String str2 = "abc";
        String str3 = new String("abc");
        String str4 = str3.intern();
        System.out.println(str1 == str2);
        //true ponieważ jest ten sam adres pamięci w string pool
        System.out.println(str1 == str3);
        //false ponieważ jest to nowy obiekt z inną referencją
        System.out.println(str1 == str4);
        //true poniważ nowy obiekt został dodany do string pool i przez to że zawiera to tamo co inna referencja zostaje ona przypisana do tego obiektu
    }
}