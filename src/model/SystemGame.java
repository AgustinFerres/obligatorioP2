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

      showBoard(pos);

      System.out.println(currentPlayer + "'s turn");
      System.out.println("Enter position:");

      pos = Position.getPosition(scanner.nextLine());
    }

  }

  public void showBoard(Position currentPos) {
    final String RESET = "\u001B[0m";
    final String RED = "\u001B[31m";
    final String GREEN = "\u001B[32m";
    final String YELLOW = "\u001B[33m";

    StringBuilder sb = new StringBuilder();
    sb.append(GREEN).append("*************************************\n").append(RESET); // Outer top border
    for (int i = 0; i < 3; i++) {
      for (int x = 0; x < 3; x++) { // Iterate rows of each 3x3 inner board
        sb.append(GREEN).append("* ").append(RESET); // Start of row
        for (int j = 0; j < 3; j++) {
          Board currentBoard = board[i][j]; // Current 3x3 board
          for (int y = 0; y < 3; y++) {
            String value = CellValue.BLANK.equals(currentBoard.getPosition(Position.getPosition(x, y))) ? " " : currentBoard.getPosition(Position.getPosition(x, y)).toString();
            sb.append(value);

            if (y < 2) {
              sb.append(" | "); // Vertical border between cells
            } else {
              sb.append(" "); // End of row
            }
          }
          sb.append(GREEN).append("* ").append(RESET); // Right border of a single 3x3 board
        }
        if (x < 2) {
          sb.append(GREEN).append("\n*").append(RESET).append("---+---+---").append(GREEN)
              .append("*").append(RESET).append("---+---+---").append(GREEN)
              .append("*").append(RESET).append("---+---+---").append(GREEN).append("*\n").append(RESET); // Horizontal border between rows
        } else {
          sb.append("\n"); // End of last row
        }
      }
      sb.append(GREEN).append("*************************************\n").append(RESET); // Bottom border between boards
    }
    System.out.println(sb.toString());
  }
}

