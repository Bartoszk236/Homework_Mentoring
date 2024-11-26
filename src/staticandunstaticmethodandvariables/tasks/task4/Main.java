package src.staticandunstaticmethodandvariables.tasks.task4;

public class Main {
    public static void main(String[] args) {
        //zmienna statyczna carCount odnosi się do całej klasy i może zostać wywołana bez tworzenia obeiktu danej klasy
        System.out.println(Car.carCount);
        Car car = new Car("Focus");
        System.out.println(Car.carCount);
        //zmienna niestastyczna odnosi się do aktualnego stanu obiektu. bez wcześniejszego utworzenia obiektu nie mogę się odwołać do model.
        System.out.println(car.model);
        Car car2 = new Car("Mondeo");
        System.out.println(car2.model);
        System.out.println(Car.carCount);
    }
}