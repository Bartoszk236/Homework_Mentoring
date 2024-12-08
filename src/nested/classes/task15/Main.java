package src.nested.classes.task15;

public class Main {
    public static void main(String[] args) {
        House house = new House("Marszałkowska 100");

        house.addRoom("Sypialnia");
        house.addRoom("Łazienka");
        house.addRoom("Salon");

        house.display();
    }
}