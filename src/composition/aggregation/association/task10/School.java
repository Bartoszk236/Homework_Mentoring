package src.composition.aggregation.association.task10;

import java.util.List;

public class School {
    private List<Classroom> classrooms;

    public School addClassroom(Classroom classroom) {
        classrooms.add(classroom);
        return this;
    }
}