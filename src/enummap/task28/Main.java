package src.enummap.task28;

import java.util.EnumMap;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        enum Status {
            WAITING,
            RUNNING,
            FINISHED
        }
        EnumMap<Status, String> enumMap = new EnumMap<>(Status.class);
        enumMap.put(Status.WAITING, "Task 1");
        enumMap.put(Status.RUNNING, "Task 2");
        enumMap.put(Status.FINISHED, "Task 3");
        Iterator<Status> iterator = enumMap.keySet().iterator();
        while (iterator.hasNext()) {
            Status status = iterator.next();
            String value = enumMap.get(status);
            System.out.println(value);
            System.out.println(status.name());
        }
    }
}