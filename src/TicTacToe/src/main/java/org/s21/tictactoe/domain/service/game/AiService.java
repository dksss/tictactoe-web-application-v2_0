package org.s21.tictactoe.domain.service.game;

import org.s21.tictactoe.domain.model.Game;
import org.s21.tictactoe.domain.model.Position;

public interface AiService {

  Position getBestMove(Game game);

}
