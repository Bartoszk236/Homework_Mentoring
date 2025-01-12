package src.queue.task13;

public class Person implements Comparable<Person> {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public Person setAge(int age) {
        this.age = age;
        return this;
    }

    public String getName() {
        return name;
    }

    public Person setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Age: " + age;
    }

    @Override
    public int compareTo(Person o) {
//        return Integer.compare(this.age, o.age); // priorytet dla osoby najmłodszej
        return Integer.compare(o.age, this.age); // priorytet dla osoby najstarszej
    }
}