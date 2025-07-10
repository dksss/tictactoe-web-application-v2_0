package org.s21.tictactoe.datasource.mapper;

import org.s21.tictactoe.datasource.entities.BoardEntity;
import org.s21.tictactoe.domain.model.Board;
import org.s21.tictactoe.domain.model.GameContsants;

public class DataBoardMapper {

  public static BoardEntity toDatasource(Board board) {
    StringBuilder data = new StringBuilder();

    for (int i = 0; i < GameContsants.HEIGHT; ++i) {
      for (int j = 0; j < GameContsants.WIDTH; ++j) {
        data.append(board.getValue(i, j));
      }
    }

    return new BoardEntity(data.toString());
  }

  public static Board toDomain(BoardEntity boardEntity) {
    int[][] fieldDomain = new int[GameContsants.HEIGHT][GameContsants.WIDTH];
    String fieldData = boardEntity.getField();

    for (int i = 0; i < GameContsants.HEIGHT; ++i) {
      for (int j = 0; j < GameContsants.WIDTH; ++j) {
        fieldDomain[i][j] = getValueFromData(fieldData, i, j);
      }
    }
    return new Board(fieldDomain);
  }

  private static int getValueFromData(String field, int y, int x) {
    int index = y * GameContsants.WIDTH + x;
    return Character.getNumericValue(field.charAt(index));
  }
}
