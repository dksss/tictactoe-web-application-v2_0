package org.s21.tictactoe.web.controller;

import lombok.RequiredArgsConstructor;
import org.s21.tictactoe.domain.exception.InvalidGameException;
import org.s21.tictactoe.domain.model.Game;
import org.s21.tictactoe.domain.model.GameState;
import org.s21.tictactoe.domain.service.game.GameService;
import org.s21.tictactoe.web.mapper.WebGameMapper;
import org.s21.tictactoe.web.model.GameWeb;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {

  private final GameService gameService;

  @PostMapping("/bot")
  public ResponseEntity<GameWeb> startGameWithAi() {
    Game game = gameService.startGame(getUserId(), GameState.PLAYING_WITH_AI);
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(WebGameMapper.toWeb(game));
  }

  @PostMapping("/player")
  public ResponseEntity<GameWeb> startGameWithPlayer() {
    Game game = gameService.startGame(getUserId(), GameState.WAITING_FOR_PLAYERS);
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(WebGameMapper.toWeb(game));
  }

  @GetMapping("/all_games")
  public ResponseEntity<List<GameWeb>> getAllGames() {
    List<Game> games = gameService.getAvailableGames(getUserId());
    List<GameWeb> gamesWeb = games.stream()
            .map(WebGameMapper::toWeb)
            .collect(Collectors.toList());

    return ResponseEntity
            .status(HttpStatus.OK)
            .body(gamesWeb);
  }

  @PostMapping("/join/{gameId}")
  public ResponseEntity<GameWeb> joinGame(@PathVariable UUID gameId) {
    GameWeb game = WebGameMapper.toWeb(gameService.joinGame(gameId, getUserId()));
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(game);
  }

  @GetMapping("/{gameId}")
  public ResponseEntity<GameWeb> getGame(@PathVariable UUID gameId) {
    Game game = gameService.getGameById(gameId);
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(WebGameMapper.toWeb(game));
  }

  @PatchMapping("/move/{gameId}")
  public ResponseEntity<GameWeb> makeMove(@PathVariable UUID gameId,
                                          @RequestBody GameWeb currentGame) {
    if (!gameId.equals(currentGame.getId())) {
      throw new InvalidGameException("Mismatch between path variable 'gameId' and 'modelGameId");
    }

    Game updatedGame = gameService.makeMove(WebGameMapper.toDomain(currentGame), getUserId());
    boolean isGameOver = gameService.isGameOver(updatedGame);
    return ResponseEntity
            .status(HttpStatus.OK)
            .header("Game-Status", isGameOver ? "gameover" : "playing")
            .body(WebGameMapper.toWeb(updatedGame));
  }

  private UUID getUserId() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UUID userId = (UUID) auth.getPrincipal();
    return userId;
  }

}
