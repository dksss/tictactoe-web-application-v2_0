package org.s21.tictactoe.web.mapper;

import org.s21.tictactoe.domain.model.Board;
import org.s21.tictactoe.domain.model.GameContsants;
import org.s21.tictactoe.web.model.BoardWeb;

public class WebBoardMapper {
  public static final String VALUE_EMPTY = " ";
  public static final String VALUE_X = "X";
  public static final String VALUE_O = "O";

  public static BoardWeb toWeb(Board source) {
    String[][] fieldWeb = new String[GameContsants.HEIGHT][GameContsants.WIDTH];

    var fieldDomain = source.getField();
    for (int i = 0; i < GameContsants.HEIGHT; i++) {
      for (int j = 0; j < GameContsants.WIDTH; j++) {
        fieldWeb[i][j] = getStringValue(fieldDomain[i][j]);
      }
    }

    return new BoardWeb(fieldWeb);
  }

  public static Board toDomain(BoardWeb boardWeb) {
    int[][] fieldDomain = new int[GameContsants.HEIGHT][GameContsants.WIDTH];

    var fieldWeb = boardWeb.getField();
    for (int i = 0; i < GameContsants.HEIGHT; i++) {
      for (int j = 0; j < GameContsants.WIDTH; j++) {
        fieldDomain[i][j] = getIntValue(fieldWeb[i][j]);
      }
    }

    return new Board(fieldDomain);
  }

  private static String getStringValue(int value) {
    return switch(value) {
      case 1 -> VALUE_X;
      case 2 -> VALUE_O;
      default -> VALUE_EMPTY;
    };
  }

  private static int getIntValue(String value) {
    return switch (value) {
      case VALUE_X -> 1;
      case VALUE_O -> 2;
      default -> 0;
    };
  }
}

