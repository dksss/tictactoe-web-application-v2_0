package org.s21.tictactoe.domain.exception;

public class InvalidMoveException extends RuntimeException {
  public InvalidMoveException(String message) {
    super(message);
  }
}
