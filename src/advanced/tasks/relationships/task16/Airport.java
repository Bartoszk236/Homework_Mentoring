package src.advanced.tasks.relationships.task16;

import java.util.ArrayList;
import java.util.List;

public class Airport {
    private List<Airplane> airplanes = new ArrayList<>();

    public Airport addAirplane(Airplane airplane) {
        airplanes.add(airplane);
        return this;
    }
}