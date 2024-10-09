import model.Player;
import model.SystemGame;

public class Main {
  public static void main(String[] args) {
    SystemGame game = new SystemGame();
    Player player1 = new Player("Player 1");
    Player player2 = new Player("Player 2");

    game.addPlayer(player1);
    game.addPlayer(player2);
    game.startGame();

  }
}
