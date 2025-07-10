package org.s21.tictactoe.datasource.mapper;

import org.s21.tictactoe.datasource.entities.UserEntity;
import org.s21.tictactoe.domain.model.User;

public class DataUserMapper {

  public static UserEntity toDatasource(User user) {
    return new UserEntity(user.getId(), user.getLogin(), user.getPassword());
  }

  public static User toDomain(UserEntity userEntity) {
    return new User(userEntity.getId(), userEntity.getLogin(), userEntity.getPassword());
  }
}
