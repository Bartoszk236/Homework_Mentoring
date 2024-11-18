package src.composition.aggregation.association.task6;

public class Person {
    private Address address;

    public Address getAddress() {
        return address;
    }

    public Person setAddress(Address address) {
        this.address = address;
        return this;
    }
}