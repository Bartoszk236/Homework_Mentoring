package src.nested.classes.task15;

import java.util.ArrayList;
import java.util.List;

public class House {
    private class Room {
        String name;

        public Room(String name) {
            this.name = name;
        }

        public void display(){
            System.out.println(name);
        }
    }

    private String address;
    private List<Room> rooms = new ArrayList<>();

    public House(String address) {
        this.address = address;
    }

    public void addRoom(String name) {
        rooms.add(new Room(name));
        System.out.println("Successfully added room " + name);
    }

    public void display() {
        System.out.println("Rooms in house: " + address);
        rooms.forEach(Room::display);
    }
}