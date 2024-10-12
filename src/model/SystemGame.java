package model;

import types.CellValue;
import types.MenuOptions;
import types.Position;
import utils.DisplayUtils;

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

  public Player getPlayer(String name, Player selectedPlayer) {
    for (Player player : players.stream().filter(player -> !player.equals(selectedPlayer)).toList()) {
      if (player.getAlias().equals(name)) {
        return player;
      }
    }

    throw new IllegalArgumentException("Player not found");
  }

  public Board getBoard(Position pos) {
    return board[pos.getX()][pos.getY()];
  }

  private Player selectPlayer(Scanner scanner, Player selectedPlayer) {
    if (selectedPlayer != null) {
      System.out.println(Arrays.toString(players.stream().filter(player -> !player.equals(selectedPlayer)).toArray()));
    } else {
      System.out.println(Arrays.toString(players.toArray()));
    }

    return getPlayer(scanner.nextLine(), selectedPlayer);
  }

  public void start() {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Bienvenidos");

    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    DisplayUtils.clearScreen();

    while (true) {
      DisplayUtils.showMenu();

      MenuOptions option = null;

      while (option == null) {
        try {
          String input = scanner.nextLine();
          option = MenuOptions.getOption(Integer.parseInt(input));
        } catch (NumberFormatException e) {
          System.out.println("Invalid input");
        }
      }

      switch (option) {
        case REGISTER_PLAYER:
          registerPlayer();
          break;
        case PLAY_GAME:
          startGame();
          break;
        case PLAY_GAME_WITH_COMPUTER:
          break;
        case SHOW_RANKING:
          break;
        case EXIT:
          System.exit(0);
          break;
      }

    }
  }

  private void startGame() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Select player 1:");
    Player player1 = selectPlayer(scanner, null);
    player1.setType(CellValue.X);

    System.out.println("Select player 2:");
    Player player2 = selectPlayer(scanner, player1);
    player2.setType(CellValue.O);

    Player currentPlayer = player1;

    System.out.println(currentPlayer.getName() + "'s turn");
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
        if (this.playerWon(currentPlayer)) {
          System.out.println(currentPlayer + " won!");
          showBoard(pos);
          currentPlayer.increaseScore();
          return;
        } else {
          currentPlayer = currentPlayer.equals(player1) ? player2 : player1;
          currentBoard = getBoard(pos);
        }
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }

      showBoard(pos);

      System.out.println(currentPlayer.getName() + "'s turn");
      System.out.println("Enter position:");

      pos = Position.getPosition(scanner.nextLine());
    }

  }

  private void showBoard(Position currentPos) {
    DisplayUtils.showBoard(board, currentPos);
  }

  private void registerPlayer() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter player name:");
    String name = scanner.nextLine();
    System.out.println("Enter player alias:");
    String alias = scanner.nextLine();

    boolean aliasExists = playerExists(alias);

    while (aliasExists) {
      System.out.println("Alias already exists. Enter a different alias:");
      alias = scanner.nextLine();
      aliasExists = playerExists(alias);
    }

    System.out.println("Enter player age:");
    int age = Integer.parseInt(scanner.nextLine());

    this.addPlayer(new Player(name, alias, age));
  }

  private boolean playerExists(String alias) {
    return this.players.stream().anyMatch(player -> player.getAlias().equals(alias));
  }

  private boolean playerWon(Player player) {
    // Check horizontal and vertical lines
    for (int i = 0; i < 3; i++) {
      if (player.equals(board[i][0].getWinner()) && player.equals(board[i][1].getWinner()) && player.equals(board[i][2].getWinner()) ||
          player.equals(board[0][i].getWinner()) && player.equals(board[1][i].getWinner()) && player.equals(board[2][i].getWinner())) {
        return true;
      }
    }

    // Check diagonal lines
    return player.equals(board[0][0].getWinner()) && player.equals(board[1][1].getWinner()) && player.equals(board[2][2].getWinner()) ||
        player.equals(board[0][2].getWinner()) && player.equals(board[1][1].getWinner()) && player.equals(board[2][0].getWinner());
  }
}

