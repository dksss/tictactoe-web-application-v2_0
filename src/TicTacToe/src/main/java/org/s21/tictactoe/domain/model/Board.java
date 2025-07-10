package org.s21.tictactoe.domain.model;

import lombok.Getter;

@Getter
public class Board {

  private final int[][] field;

  public Board() {
    this.field = new int[GameContsants.HEIGHT][GameContsants.WIDTH];
  }

  public Board(int[][] field) {
    this.field = field;
  }

  public void setValue(Position pos, int value) {
    int y = pos.y();
    int x = pos.x();

    field[y][x] = value;
  }

  public int getValue(int y, int x) {
    return field[y][x];
  }
}
