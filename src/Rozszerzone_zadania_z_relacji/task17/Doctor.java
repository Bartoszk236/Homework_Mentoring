package src.Rozszerzone_zadania_z_relacji.task17;

import java.util.ArrayList;
import java.util.List;

public class Doctor {
    private List<Patient> patients = new ArrayList<>();

    public Doctor assignPatient(Patient patient) {
        patients.add(patient);
        return this;
    }
}
