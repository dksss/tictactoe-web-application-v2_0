package org.s21.tictactoe.web.mapper;

import org.s21.tictactoe.domain.model.Game;
import org.s21.tictactoe.web.model.GameWeb;

public class WebGameMapper {

  public static GameWeb toWeb(Game game) {
    var id = game.getId();
    var board = WebBoardMapper.toWeb(game.getBoard());

    return new GameWeb(id, board);
  }

  public static Game toDomain(GameWeb gameWeb) {
    var id = gameWeb.getId();
    var board = WebBoardMapper.toDomain(gameWeb.getBoard());

    return new Game(id, board);
  }
}
