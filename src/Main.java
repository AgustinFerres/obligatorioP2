import model.Player;
import model.SystemGame;
import utils.DisplayUtils;

import java.util.Comparator;
import java.util.List;

public class Main {
  public static void main(String[] args) {
    SystemGame game = new SystemGame();

    Player player1 = new Player("John Doe", "John", 25);
    Player player2 = new Player("Jane Doe", "Jane Doe", 23);

    player1.increaseScore();
    player1.increaseScore();

    List<Player> players = List.of(player1, player2);

    game.start();

//    DisplayUtils.showMenu();
//    DisplayUtils.clearScreen();
//    DisplayUtils.showRanking(players.stream().sorted(Comparator.comparingInt(Player::getScore).reversed()).toList());
  }
}
