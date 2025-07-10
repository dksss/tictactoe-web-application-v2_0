package org.s21.tictactoe.domain.service.game;

import org.s21.tictactoe.domain.model.Board;
import org.s21.tictactoe.domain.model.Game;
import org.s21.tictactoe.domain.model.GameContsants;
import org.s21.tictactoe.domain.model.Position;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class MinimaxAiService implements AiService {

  @Override
  public Position getBestMove(Game game) {
    Position bestMove = null;
    int bestScore = Integer.MIN_VALUE;

    var board = game.getBoard();
    for (int i = 0; i < GameContsants.HEIGHT; ++i) {
      for (int j = 0; j < GameContsants.WIDTH; ++j) {
        if (board.getValue(i, j) == GameContsants.EMPTY) {
          Position pos = new Position(i, j);
          board.setValue(pos, GameContsants.O);
          int score = minimax(board, false);
          board.setValue(pos, GameContsants.EMPTY);

          if (score > bestScore) {
            bestScore = score;
            bestMove = pos;
          }
        }
      }
    }

    return bestMove;
  }

  private int minimax(Board board, boolean isAiTurn) {
    int score = evaluate(board);
    if (Math.abs(score) == 10 || GameStateService.isBoardFilled(board)) {
      return score;
    }

    if (isAiTurn) {
      int highestScore = Integer.MIN_VALUE;
      for (int i = 0; i < GameContsants.HEIGHT; i++) {
        for (int j = 0; j < GameContsants.WIDTH; j++) {
          if (board.getValue(i, j) == GameContsants.EMPTY) {
            Position pos = new Position(i, j);
            board.setValue(pos, GameContsants.O);
            highestScore = Math.max(highestScore, minimax(board, false));
            board.setValue(pos, GameContsants.EMPTY);
          }
        }
      }
      return highestScore;
    } else {
      int lowestScore = Integer.MAX_VALUE;
      for (int i = 0; i < GameContsants.HEIGHT; i++) {
        for (int j = 0; j < GameContsants.WIDTH; j++) {
          if (board.getValue(i, j) == GameContsants.EMPTY) {
            Position pos = new Position(i, j);
            board.setValue(pos, GameContsants.X);
            lowestScore = Math.min(lowestScore, minimax(board, true));
            board.setValue(pos, GameContsants.EMPTY);
          }
        }
      }
      return lowestScore;
    }
  }

  private int evaluate(Board board) {
    int score = 0;

    if (GameStateService.isWinning(board, GameContsants.X)) {
      score = -10;
    } else if (GameStateService.isWinning(board, GameContsants.O)) {
      score = 10;
    }

    return score;
  }

}
