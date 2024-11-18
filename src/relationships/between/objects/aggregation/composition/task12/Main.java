package src.relationships.between.objects.aggregation.composition.task12;

public class Main {
    public static void main(String[] args) {
        City city = new City();
        Building building = new Building();
        Building building2 = new Building();

        building2.setAddress("Warszawa");
        building.setAddress("Warszawa");
        city.addBuilding(building);
        city.addBuilding(building2);

        System.out.println(city.getBuildingsByAddress("Warszawa"));
    }
}