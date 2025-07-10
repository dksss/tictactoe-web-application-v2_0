package org.s21.tictactoe.web.controller.advice;

import org.s21.tictactoe.domain.exception.AccessDeniedException;
import org.s21.tictactoe.domain.exception.InvalidMoveException;
import org.s21.tictactoe.domain.exception.InvalidUserException;
import org.s21.tictactoe.domain.exception.InvalidGameException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

  @ExceptionHandler(InvalidGameException.class)
  public ResponseEntity<String> exceptionInvalidGameHandler(InvalidGameException ex) {
    return ResponseEntity
            .badRequest()
            .body(ex.getMessage());
  }

  @ExceptionHandler(InvalidMoveException.class)
  public ResponseEntity<String> exceptionInvalidMoveHandler(InvalidMoveException ex) {
    return ResponseEntity
            .badRequest()
            .body(ex.getMessage());
  }

  @ExceptionHandler(InvalidUserException.class)
  public ResponseEntity<String> exceptionInvalidUserHandler(InvalidUserException ex) {
    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ex.getMessage());
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<String> exceptionInvalidAuthHandler(AccessDeniedException ex) {
    return ResponseEntity
            .badRequest()
            .body(ex.getMessage());
  }

}
