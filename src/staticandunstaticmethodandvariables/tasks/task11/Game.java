package src.staticandunstaticmethodandvariables.tasks.task11;

public class Game {
    public static int maxPlayers;
    public String playerName;

    static {
        maxPlayers = 3;
        System.out.println("Max players: " + maxPlayers);
    }
}