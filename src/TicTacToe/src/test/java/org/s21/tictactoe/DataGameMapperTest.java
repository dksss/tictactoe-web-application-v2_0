package org.s21.tictactoe;

import org.junit.jupiter.api.Test;
import org.s21.tictactoe.datasource.entities.BoardEntity;
import org.s21.tictactoe.datasource.entities.GameEntity;
import org.s21.tictactoe.datasource.mapper.DataGameMapper;
import org.s21.tictactoe.domain.model.Board;
import org.s21.tictactoe.domain.model.Game;
import org.s21.tictactoe.domain.model.GameState;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.s21.tictactoe.domain.model.GameState.PLAYING_WITH_AI;

public class DataGameMapperTest {

  @Test
  void toDatasource_mapAllFields() {
    UUID gameId = UUID.randomUUID();
    Board board = new Board();
    GameState state = PLAYING_WITH_AI;
    UUID hostPlayerId = UUID.randomUUID();
    boolean isDraw = false;
    UUID winnerId = null;
    UUID playerX = hostPlayerId;
    UUID playerO = null;

    Game game = new Game(gameId, board, state, hostPlayerId, isDraw, winnerId, playerX, playerO);
    GameEntity testData = DataGameMapper.toDatasource(game);

    assertThat(testData.getId()).isEqualTo(game.getId());
    assertThat(testData.getState()).isEqualTo(game.getState().name());
    assertThat(testData.getCurrentPlayerId()).isEqualTo(game.getCurrentPlayerId());
    assertThat(testData.isDraw()).isEqualTo(game.isDraw());
    assertThat(testData.getWinnerId()).isEqualTo(game.getWinnerId());
    assertThat(testData.getXMarkPlayerId()).isEqualTo(game.getXMarkPlayerId());
    assertThat(testData.getOMarkPlayerId()).isEqualTo(game.getOMarkPlayerId());
  }

  @Test
  void toDomain_mapAllFields() {
    UUID gameId = UUID.randomUUID();
    BoardEntity board = new BoardEntity("000000000");
    String state = GameState.PLAYING_WITH_AI.name();
    UUID hostPlayerId = UUID.randomUUID();
    boolean isDraw = false;
    UUID winnerId = null;
    UUID playerX = hostPlayerId;
    UUID playerO = null;

    GameEntity game = new GameEntity(gameId, board, state, hostPlayerId, isDraw, winnerId, playerX, playerO);
    Game test = DataGameMapper.toDomain(game);

    assertThat(test.getId()).isEqualTo(game.getId());
    assertThat(test.getState()).isEqualTo(GameState.valueOf(game.getState()));
    assertThat(test.getCurrentPlayerId()).isEqualTo(game.getCurrentPlayerId());
    assertThat(test.isDraw()).isEqualTo(game.isDraw());
    assertThat(test.getWinnerId()).isEqualTo(game.getWinnerId());
    assertThat(test.getXMarkPlayerId()).isEqualTo(game.getXMarkPlayerId());
    assertThat(test.getOMarkPlayerId()).isEqualTo(game.getOMarkPlayerId());
  }

}
