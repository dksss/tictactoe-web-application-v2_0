package org.s21.tictactoe.datasource.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "games")
public class GameEntity {

  @Id
  private UUID id;

  @Embedded
  private BoardEntity boardEntity;

  @Column(name = "game_state", nullable = false)
  private String state;

  @Column(name = "turn")
  private UUID currentPlayerId;

  @Column(name = "is_draw")
  private boolean isDraw;

  @Column(name = "winner")
  private UUID winnerId;

  @Column(name = "x_mark", nullable = false)
  private UUID xMarkPlayerId;

  @Column(name = "o_mark")
  private UUID oMarkPlayerId;

}
