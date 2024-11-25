package src.staticandunstaticmethodandvariables.tasks.task3;

public class Person {
    private String name;
    public static int population;

    public static void increment(){
        population++;
    }
    public Person(String name){
        this.name = name;
        increment();
    }
}