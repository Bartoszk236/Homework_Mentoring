package src.Rozszerzone_zadania_z_relacji.task20;

import java.util.ArrayList;
import java.util.List;

public class Project {
    private List<TeamMember> teamMembers = new ArrayList<>();

    public Project addTeamMember(TeamMember teamMember){
        teamMembers.add(teamMember);
        return this;
    }

    public List<TeamMember> getTeamMembers() {
        return teamMembers;
    }
}
