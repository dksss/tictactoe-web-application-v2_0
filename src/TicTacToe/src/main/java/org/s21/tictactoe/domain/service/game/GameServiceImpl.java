package org.s21.tictactoe.domain.service.game;

import lombok.RequiredArgsConstructor;
import org.s21.tictactoe.datasource.mapper.DataGameMapper;
import org.s21.tictactoe.datasource.repository.GameRepository;
import org.s21.tictactoe.domain.model.*;
import org.s21.tictactoe.domain.exception.InvalidGameException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@SessionScope
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

  private final AiService aiService;
  private final GameRepository gameRepository;

  @Override
  public Game startGame(UUID playerId, GameState state) {
    Game game = new Game(playerId, state);
    gameRepository.save(DataGameMapper.toDatasource(game));

    return game;
  }

  @Override
  public List<Game> getAvailableGames(UUID playerId) {
    return gameRepository.getAvailableGames(playerId).stream()
            .map(DataGameMapper::toDomain)
            .collect(Collectors.toList());
  }

  @Override
  public Game joinGame(UUID gameId, UUID playerId) throws InvalidGameException {
    Game existGame = getGameById(gameId);

    if(GameValidator.isValidGameJoiner(existGame, playerId)) {
      existGame.setState(GameState.PLAYING_WITH_PLAYER);
      existGame.setOMarkPlayerId(playerId);
    } else {
      throw new InvalidGameException("You can't join this game (Game started or you're already in a game)");
    }

    gameRepository.save(DataGameMapper.toDatasource(existGame));

    return existGame;
  }

  @Override
  public boolean isValid(Game currentGame) throws InvalidGameException {
    Game existingGame = getGameById(currentGame.getId());
    return GameValidator.isValidGame(currentGame, existingGame);
  }

  @Override
  public Game getGameById(UUID id) throws InvalidGameException {
    return gameRepository.findById(id)
            .map(DataGameMapper::toDomain)
            .orElseThrow(() -> new InvalidGameException("Invalid game id: " + id));
  }

  @Override
  public Game makeAiMove(Game game) {
    if (!isGameOver(game)) {
      Position move = aiService.getBestMove(game);
      game.makeMove(move, GameContsants.O);
    }

    gameRepository.save(DataGameMapper.toDatasource(game));

    return game;
  }

  @Override
  public Game makeMove(Game game, UUID playerId) throws InvalidGameException {
    if (!isValid(game)) {
      throw new InvalidGameException("Invalid move or invalid field.");
    }

    Game existingGame = getGameById(game.getId());
    if (!GameValidator.checkOrderOfMove(existingGame, playerId)) {
      throw new InvalidGameException("It's not your turn.");
    }

    existingGame.setBoard(game.getBoard());
    gameRepository.save(DataGameMapper.toDatasource(existingGame));

    return processMove(existingGame);
  }

  @Override
  public boolean isGameOver(Game game) {
    return GameStateService.checkGameOver(game);
  }

  private Game processMove(Game existingGame) throws InvalidGameException {
    return switch(existingGame.getState()) {
      case PLAYING_WITH_PLAYER -> processPlayerMove(existingGame);
      case PLAYING_WITH_AI -> makeAiMove(existingGame);
      default -> throw new InvalidGameException("Invalid game state: " + existingGame.getState());
    };
  }

  private Game processPlayerMove(Game existingGame) {
    if (!isGameOver(existingGame)) {
      UUID playerX = existingGame.getXMarkPlayerId();
      UUID playerO = existingGame.getOMarkPlayerId();
      UUID nextTurnPlayerId = existingGame.getCurrentPlayerId().equals(playerX) ? playerO : playerX;

      existingGame.setCurrentPlayerId(nextTurnPlayerId);

      gameRepository.save(DataGameMapper.toDatasource(existingGame));
    }

    return updateGame(existingGame);
  }

  private Game updateGame(Game game) {
    if (game.getState() != GameState.GAME_OVER) {
      gameRepository.save(DataGameMapper.toDatasource(game));
    }

    return game;
  }

}
