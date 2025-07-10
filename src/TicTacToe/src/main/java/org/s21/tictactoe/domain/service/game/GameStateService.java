package org.s21.tictactoe.domain.service.game;

import org.s21.tictactoe.domain.model.Board;
import org.s21.tictactoe.domain.model.Game;
import org.s21.tictactoe.domain.model.GameContsants;
import org.s21.tictactoe.domain.model.GameState;

public class GameStateService {

  public static boolean checkGameOver(Game game) {
    Board board = game.getBoard();

    if (isWinning(board, GameContsants.X)) {
      game.setState(GameState.GAME_OVER);
      game.setWinnerId(game.getXMarkPlayerId());
      game.setDraw(false);
      return true;
    }

    if (isWinning(board, GameContsants.O)) {
      game.setState(GameState.GAME_OVER);
      game.setWinnerId(game.getOMarkPlayerId());
      game.setDraw(false);
      return true;
    }

    if (isBoardFilled(board)) {
      game.setState(GameState.GAME_OVER);
      game.setDraw(true);
      game.setWinnerId(null);
      return true;
    }

    return false;
  }

  public static boolean isBoardFilled(Board board) {
    var field = board.getField();
    for (int i = 0; i < GameContsants.HEIGHT; i++) {
      for (int j = 0; j < GameContsants.WIDTH; j++) {
        if (field[i][j] == GameContsants.EMPTY) {
          return false;
        }
      }
    }

    return true;
  }

  public static boolean isWinning(Board board, int playerValue) {
    var field = board.getField();
    return (checkLines(field, playerValue) || checkColumns(field, playerValue)
            || checkDiagonalLines(field, playerValue));
  }

  private static boolean checkLines(int[][] field, int value) {
    boolean check = false;
    for (int i = 0; i < GameContsants.HEIGHT; i++) {
      if (field[i][0] == value && field[i][0] == field[i][1] && field[i][1] == field[i][2]) {
        check = true;
        break;
      }
    }

    return check;
  }

  private static boolean checkColumns(int[][] field, int value) {
    boolean check = false;
    for (int j = 0; j < GameContsants.WIDTH; j++) {
      if (field[0][j] == value && field[0][j] == field[1][j] && field[1][j] == field[2][j]) {
        check = true;
        break;
      }
    }

    return check;
  }

  private static boolean checkDiagonalLines(int[][] field, int value) {
    return ((field[0][0] == value && field[0][0] == field[1][1] && field[1][1] == field[2][2]) ||
            (field[0][2] == value && field[0][2] == field[1][1] && field[1][1] == field[2][0]));
  }

}
