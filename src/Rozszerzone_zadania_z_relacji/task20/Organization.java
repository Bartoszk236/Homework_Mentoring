package src.Rozszerzone_zadania_z_relacji.task20;

import java.util.ArrayList;
import java.util.List;

public class Organization {
    private List<Project> projects = new ArrayList<>();

    public Organization addProject(Project project) {
        projects.add(project);
        return this;
    }

    public List<TeamMember> getTeamMembersByProject(Project project) {
        return project.getTeamMembers();
    }
}
