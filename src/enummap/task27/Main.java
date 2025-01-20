package src.enummap.task27;

import java.util.EnumMap;

public class Main {
    public static void main(String[] args) {
        enum Seasons {
            SPRING,SUMMER,AUTUMN,WINTER;
        }
        EnumMap<Seasons, String> seasonsMap = new EnumMap<>(Seasons.class);
        seasonsMap.put(Seasons.SPRING, "Spring is season after winter");
        seasonsMap.put(Seasons.SUMMER, "Summer is the time when we have the highest temperature");
        seasonsMap.put(Seasons.AUTUMN, "Autumn is the time when every tree starting have a leafs");
        seasonsMap.put(Seasons.WINTER, "Winter is the time when we have a snow");
        seasonsMap.forEach((key, value) -> System.out.println("Season: " + key + " -> Description: " + value));
    }
}