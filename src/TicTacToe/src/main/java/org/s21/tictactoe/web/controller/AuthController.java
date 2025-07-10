package org.s21.tictactoe.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.s21.tictactoe.domain.service.user.AuthorizationService;
import org.s21.tictactoe.web.model.SignUpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AuthController {

  private final AuthorizationService authorizationService;

  @PostMapping("/registration")
  public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpRequest request) {
    boolean isRegistered = authorizationService.registerUser(request);

    return isRegistered
            ? ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully")
            : ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestHeader("Authorization") String authHeader) {
    UUID userId = authorizationService.processAuthorization(authHeader);
    return ResponseEntity
            .status(HttpStatus.OK)
            .body("Your id: " + userId.toString());
  }

}
