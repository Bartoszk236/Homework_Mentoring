package src.Rozszerzone_zadania_z_relacji.task16;

import java.util.ArrayList;
import java.util.List;

public class Airport {
    private List<Airplane> airplanes = new ArrayList<>();

    public Airport addAirplane(Airplane airplane) {
        airplanes.add(airplane);
        return this;
    }
}
