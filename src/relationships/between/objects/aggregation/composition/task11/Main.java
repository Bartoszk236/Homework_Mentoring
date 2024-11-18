package src.relationships.between.objects.aggregation.composition.task11;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        House house = new House();
        Room room = new Room();
        Room room2 = new Room();
        Room room3 = new Room();

        room.setArea(new BigDecimal("15"));
        room2.setArea(new BigDecimal("17.5"));
        room3.setArea(new BigDecimal("23.8"));

        house.addRoom(room);
        house.addRoom(room2);
        house.addRoom(room3);

        System.out.println(house.getTotalArea());
    }
}