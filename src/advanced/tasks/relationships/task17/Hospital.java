package src.advanced.tasks.relationships.task17;

import java.util.ArrayList;
import java.util.List;

public class Hospital {
    private List<Doctor> doctors = new ArrayList<>();

    public Hospital addDoctor(Doctor doctor){
        doctors.add(doctor);
        return this;
    }
}