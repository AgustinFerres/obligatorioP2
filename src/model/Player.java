package model;

import types.CellValue;

public class Player {

  private final String name;
  private CellValue type;
  private int score;

  public Player(String name) {
    this.name = name;
    this.score = 0;
  }

  public String getName() {
    return name;
  }

  public int getScore() {
    return score;
  }

  public void increaseScore() {
    score++;
  }

  public void setType(CellValue type) {
    this.type = type;
  }

  public CellValue getType() {
    return type;
  }

  @Override
  public String toString() {
    return name;
  }
}
