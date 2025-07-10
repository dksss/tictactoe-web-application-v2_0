package org.s21.tictactoe.datasource.mapper;

import org.s21.tictactoe.datasource.entities.BoardEntity;
import org.s21.tictactoe.datasource.entities.GameEntity;
import org.s21.tictactoe.domain.model.Board;
import org.s21.tictactoe.domain.model.Game;
import org.s21.tictactoe.domain.model.GameState;

import java.util.UUID;

public class DataGameMapper {

  public static GameEntity toDatasource(Game game) {
    UUID id = game.getId();
    BoardEntity board = DataBoardMapper.toDatasource(game.getBoard());

    String state = game.getState().name();
    UUID currentPlayerId = game.getCurrentPlayerId();
    boolean isDraw = game.isDraw();

    UUID winnerId = game.getWinnerId();
    UUID xMarkPlayerId = game.getXMarkPlayerId();
    UUID oMarkPlayerId = game.getOMarkPlayerId();

    return new GameEntity(id, board, state, currentPlayerId, isDraw, winnerId, xMarkPlayerId, oMarkPlayerId);
  }

  public static Game toDomain(GameEntity gameEntity) {
    UUID id = gameEntity.getId();
    Board board = DataBoardMapper.toDomain(gameEntity.getBoardEntity());

    GameState state = GameState.valueOf(gameEntity.getState());
    UUID currentPlayerId = gameEntity.getCurrentPlayerId();
    boolean isDraw = gameEntity.isDraw();

    UUID winnerId = gameEntity.getWinnerId();
    UUID xMarkPlayerId = gameEntity.getXMarkPlayerId();
    UUID oMarkPlayerId = gameEntity.getOMarkPlayerId();

    return new Game(id, board, state, currentPlayerId, isDraw, winnerId, xMarkPlayerId, oMarkPlayerId);
  }
}
