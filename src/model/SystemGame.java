package model;

import types.CellValue;
import types.Position;

import java.util.*;

public class SystemGame {

  private final Board[][] board = new Board[3][3];
  private final List<Player> players = new ArrayList<>();

  public SystemGame() {
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        board[i][j] = new Board();
      }
    }

  }

  public void addPlayer(Player player) {
    players.add(player);
  }

  public List<Player> getPlayers() {
    return players;
  }

  public Player getPlayer(String name) {
    for (Player player : players) {
      if (player.getName().equals(name)) {
        return player;
      }
    }

    throw new IllegalArgumentException("Player not found");
  }

  public Board getBoard(Position pos) {
    return board[pos.getX()][pos.getY()];
  }

  private Player selectPlayer(Scanner scanner) {
    System.out.println(Arrays.toString(players.toArray()));
    return getPlayer(scanner.nextLine());
  }

  public void startGame() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Select player 1:");
    Player player1 = selectPlayer(scanner);
    player1.setType(CellValue.X);

    System.out.println("Select player 2:");
    Player player2 = selectPlayer(scanner);
    player2.setType(CellValue.O);

    Player currentPlayer = player1;

    System.out.println(currentPlayer + "'s turn");
    System.out.println("Enter position:");
    String[] input = scanner.nextLine().split(", ");

    if (input.length != 2) {
      throw new IllegalArgumentException("Invalid input");
    }

    Board currentBoard = getBoard(Objects.requireNonNull(Position.getPosition(input[0])));
    Position pos = Position.getPosition(input[1]);

    while (true) {

      try {
        currentBoard.play(currentPlayer, pos);
        if (currentBoard.playerWon(currentPlayer)) {
          System.out.println(currentPlayer + " won!");
          currentPlayer.increaseScore();
          return;
        } else {
          currentPlayer = currentPlayer == player1 ? player2 : player1;
          currentBoard = getBoard(pos);
        }
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }

      System.out.println(currentBoard);

      System.out.println(currentPlayer + "'s turn");
      System.out.println("Enter position:");

      pos = Position.getPosition(scanner.nextLine());
    }

  }
}

