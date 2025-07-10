package org.s21.tictactoe.domain.service.game;

import org.s21.tictactoe.domain.model.Game;
import org.s21.tictactoe.domain.model.GameState;

import java.util.List;
import java.util.UUID;

public interface GameService {

  Game startGame(UUID playerId, GameState state);

  List<Game> getAvailableGames(UUID playerId);

  Game joinGame(UUID gameId, UUID playerId);

  Game makeAiMove(Game game);

  Game makeMove(Game game, UUID playerId);

  boolean isValid(Game game);

  boolean isGameOver(Game game);

  Game getGameById(UUID id);
}
