package org.s21.tictactoe.domain.service.game;

import org.s21.tictactoe.domain.exception.InvalidGameException;
import org.s21.tictactoe.domain.model.Board;
import org.s21.tictactoe.domain.model.Game;
import org.s21.tictactoe.domain.model.GameContsants;
import org.s21.tictactoe.domain.model.GameState;

import java.util.UUID;

public class GameValidator {

  public static boolean isValidGame(Game currentGame, Game storedGame) {
    Board currentBoard = currentGame.getBoard();
    Board storedBoard = storedGame.getBoard();

    return isValidField(currentBoard.getField(), storedBoard.getField())
            && !isMissingMove(currentBoard.getField(), storedBoard.getField())
            && !isInvalidMarker(currentBoard);
  }

  public static boolean isValidGameJoiner(Game game, UUID playerId) {
    if (game.getState() != GameState.WAITING_FOR_PLAYERS) {
      return false;
    }

    return (game.getOMarkPlayerId() == null && !game.getXMarkPlayerId().equals(playerId));
  }

  public static boolean checkOrderOfMove(Game game, UUID playerId) throws InvalidGameException {
    UUID playerX = game.getXMarkPlayerId();
    UUID playerO = game.getOMarkPlayerId();

    if (!playerId.equals(playerX) && !playerId.equals(playerO)) {
      throw new InvalidGameException("You are not a player in this game.");
    }

    return game.getCurrentPlayerId().equals(playerId);
  }

  private static boolean isValidField(int[][] currentField, int[][] storedField) {
    for (int i = 0; i < GameContsants.HEIGHT; i++) {
      for (int j = 0; j < GameContsants.WIDTH; j++) {
        if (storedField[i][j] != GameContsants.EMPTY && storedField[i][j] != currentField[i][j]) {
          return false;
        }
      }
    }

    return true;
  }

  private static boolean isMissingMove(int[][] currentField, int[][] storedField) {
    int currentEmptyCells = countEmptyCells(currentField);
    int storedEmptyCells = countEmptyCells(storedField);

    return (currentEmptyCells == storedEmptyCells);
  }

  private static int countEmptyCells(int[][] field) {
    int emptyCeils = 0;

    for (int i = 0; i < GameContsants.HEIGHT; i++) {
      for (int j = 0; j < GameContsants.WIDTH; j++) {
        if (field[i][j] == GameContsants.EMPTY) {
          emptyCeils++;
        }
      }
    }

    return emptyCeils;
  }

  private static boolean isInvalidMarker(Board currentBoard) {
    int markX = 0;
    int markO = 0;

    int[][] field = currentBoard.getField();
    for (int i = 0; i < GameContsants.HEIGHT; i++) {
      for (int j = 0; j < GameContsants.WIDTH; j++) {
        if (field[i][j] == GameContsants.X) {
          markX++;
        } else if (field[i][j] == GameContsants.O) {
          markO++;
        }
      }
    }

    return (Math.abs(markX - markO) > 1);
  }

}
