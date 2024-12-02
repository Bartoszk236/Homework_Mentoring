package src.objectclass.task24;

public class Main {
    public static void main(String[] args) {
        Person person1 = new Person("Bartosz", "Kocylo");
        Person person2 = new Person("Bartosz", "Kocylo");

        System.out.println(person1.equals(person2));
    }
}