package org.s21.tictactoe.web.controller;

import lombok.RequiredArgsConstructor;
import org.s21.tictactoe.domain.service.user.UserService;
import org.s21.tictactoe.web.mapper.WebUserMapper;
import org.s21.tictactoe.web.model.UserWeb;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/{userId}")
  public ResponseEntity<UserWeb> getInfo(@PathVariable UUID userId) {
    UserWeb user = WebUserMapper.toWeb(userService.getUserById(userId));
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(user);
  }
}
