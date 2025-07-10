package org.s21.tictactoe.web.mapper;

import org.s21.tictactoe.domain.model.User;
import org.s21.tictactoe.web.model.UserWeb;

public class WebUserMapper {

  public static UserWeb toWeb(User user) {
    return new UserWeb(user.getId(), user.getLogin());
  }

}
