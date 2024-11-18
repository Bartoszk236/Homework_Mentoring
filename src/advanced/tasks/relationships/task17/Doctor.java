package src.advanced.tasks.relationships.task17;

import java.util.ArrayList;
import java.util.List;

public class Doctor {
    private List<Patient> patients = new ArrayList<>();

    public Doctor assignPatient(Patient patient) {
        patients.add(patient);
        return this;
    }
}