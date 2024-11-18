package src.relationships.between.objects.aggregation.composition.task11;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class House {
    private List<Room> rooms = new ArrayList<>();

    public House addRoom(Room room) {
        rooms.add(room);
        return this;
    }

    public BigDecimal getTotalArea(){
        BigDecimal totalArea = BigDecimal.ZERO;
        for (Room room : rooms) {
            totalArea = totalArea.add(room.getArea());
        }
        return totalArea;
    }
}