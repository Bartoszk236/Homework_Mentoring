package src.Relacje_między_obiektami_Agregacja_i_Kompozycja.task12;

import java.util.ArrayList;
import java.util.List;

public class City {
    private List<Building> buildings = new ArrayList<>();

    public City addBuilding(Building building) {
        buildings.add(building);
        return this;
    }

    public List<Building> getBuildingsByAddress(String address) {
        return buildings.stream().filter(building -> building.getAddress().equals(address)).toList();
    }
}
