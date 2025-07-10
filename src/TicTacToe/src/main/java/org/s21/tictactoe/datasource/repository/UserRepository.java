package org.s21.tictactoe.datasource.repository;

import org.s21.tictactoe.datasource.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, UUID> {

  Optional<UserEntity> findByLogin(String login);
  boolean existsByLogin(String login);

}
