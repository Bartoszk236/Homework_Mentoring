package src.composition.aggregation.association.task6;

public class Main {
    public static void main(String[] args) {
        Address address = new Address("KEN", "Warszawa", "02-722");
        Person person = new Person();
        person.setAddress(address);
    }
}