package src.staticandunstaticmethodandvariables.tasks.task3;

public class Person {
    private String name;
    public static int population;

    public Person(String name){
        this.name = name;
        increment();
    }

    public static void increment(){
        population++;
    }
}