package src.Relacje_między_obiektami_Agregacja_i_Kompozycja.task13;

import java.util.List;

public class Department {
    private List<Professor> professorList;

    public Department addProfessor(Professor professor) {
        professorList.add(professor);
        return this;
    }
}
