package org.s21.tictactoe.datasource.repository;

import org.s21.tictactoe.datasource.entities.GameEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GameRepository extends CrudRepository<GameEntity, UUID> {

  @Query("SELECT DISTINCT g "
          + "FROM GameEntity g "
          + "WHERE g.xMarkPlayerId = :playerId OR g.oMarkPlayerId = :playerId")
  List<GameEntity> getAvailableGames(@Param("playerId") UUID playerId);

}
