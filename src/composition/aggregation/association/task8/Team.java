package src.composition.aggregation.association.task8;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private List<Player> players = new ArrayList<>();

    public Team addPlayer(Player player) {
        players.add(player);
        return this;
    }

    public List<Player> getPlayersByPosition(String position) {
        return players.stream().filter(player -> player.getPosition().equals(position)).toList();
    }
}