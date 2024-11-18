package src.relationships.between.objects.aggregation.composition.task13;

import java.util.List;

public class Department {
    private List<Professor> professorList;

    public Department addProfessor(Professor professor) {
        professorList.add(professor);
        return this;
    }
}