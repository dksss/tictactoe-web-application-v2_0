package org.s21.tictactoe.domain.service.user;

import org.s21.tictactoe.domain.model.User;

import java.util.UUID;

public interface UserService {

  User getUserByLogin(String login);
  User getUserById(UUID userId);
  void save(User user);
  boolean isUserExists(String login);

}
