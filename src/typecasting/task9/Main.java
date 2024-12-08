package src.typecasting.task9;

public class Main {
    public static void main(String[] args) {
        Person person1 = new Student("Bartosz", 1);
        Person person2 = new Teacher("Jakub");

        Person[] persons = {person1, person2};

        for (Person person : persons) {
            if (person instanceof Student) {
                ((Student) person).getDuty();
            }
            if (person instanceof Teacher) {
                ((Teacher) person).getWish();
            }
        }
    }
}