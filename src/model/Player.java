package model;

import types.CellValue;

public class Player {

  private final String name;
  private String alias;
  private CellValue type;
  private int age;
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

  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public CellValue getType() {
    return type;
  }

  @Override
  public String toString() {
    return name;
  }
}
