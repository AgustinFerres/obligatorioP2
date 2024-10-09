package model;

import types.Position;
import types.CellValue;

public class Board {

  private final CellValue[][] board = new CellValue[3][3];

  public Board() {
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        board[i][j] = CellValue.BLANK;
      }
    }
  }

  public CellValue getPosition(Position pos) {
    return board[pos.getX()][pos.getY()];
  }

  private void setPosition(Position pos, CellValue cellValue) {
    if (cellValue == CellValue.BLANK) {
      throw new IllegalArgumentException("Invalid cell value");
    }

    if (board[pos.getX()][pos.getY()] != CellValue.BLANK) {
      throw new IllegalArgumentException("Position already taken");
    }

    board[pos.getX()][pos.getY()] = cellValue;
  }

  public boolean playerWon(Player player) {
    CellValue type = player.getType();

    // Check horizontal and vertical lines
    for (int i = 0; i < 3; i++) {
      if ((board[i][0] == type && board[i][1] == type && board[i][2] == type) ||
          (board[0][i] == type && board[1][i] == type && board[2][i] == type)) {
        return true;
      }
    }

    // Check diagonal lines
    return (board[0][0] == type && board[1][1] == type && board[2][2] == type) ||
        (board[0][2] == type && board[1][1] == type && board[2][0] == type);
  }

  public void play(Player player, Position pos) {
    setPosition(pos, player.getType());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        sb.append(board[i][j]);
        if (j < 2) {
          sb.append(" | ");
        }
      }
      if (i < 2) {
        sb.append("\n");
      }
    }
    return sb.toString();
  }
}
