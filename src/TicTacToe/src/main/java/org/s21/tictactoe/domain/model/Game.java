package org.s21.tictactoe.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class Game {

  private UUID id;
  private Board board;

  GameState state;
  private UUID currentPlayerId;
  private boolean isDraw;
  private UUID winnerId;

  private UUID xMarkPlayerId;
  private UUID oMarkPlayerId;

  public Game(UUID playerId, GameState state) {
    this.id = UUID.randomUUID();
    this.board = new Board();

    this.state = state;
    this.currentPlayerId = playerId;
    this.isDraw = false;
    this.winnerId = null;

    this.xMarkPlayerId = playerId;
    this.oMarkPlayerId = null;
  }

  public Game(UUID id, Board board) {
    this.id = id;
    this.board = board;
  }

  public void makeMove(Position pos, int value) {
    board.setValue(pos, value);
  }

}
