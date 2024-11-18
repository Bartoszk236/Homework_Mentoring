package src.composition.aggregation.association.task8;

public class Main {
    public static void main(String[] args) {
        Team team = new Team();
        Player player = new Player();
        Player player2 = new Player();
        Player player3 = new Player();
        Player player4 = new Player();
        Player player5 = new Player();
        Player player6 = new Player();
        player.setPosition("Obrońca");
        player2.setPosition("Napastnik");
        player3.setPosition("Obrońca");
        player4.setPosition("Pomocnik");
        player5.setPosition("Obrońca");
        player6.setPosition("Napastnik");

        team.addPlayer(player);
        team.addPlayer(player2);
        team.addPlayer(player3);
        team.addPlayer(player4);
        team.addPlayer(player5);
        team.addPlayer(player6);

        team.getPlayersByPosition("Pomocnik").forEach(player1 -> System.out.println(player1.getPosition()));
    }
}