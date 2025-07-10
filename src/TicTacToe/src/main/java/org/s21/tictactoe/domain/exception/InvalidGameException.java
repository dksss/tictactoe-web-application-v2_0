package org.s21.tictactoe.domain.exception;

public class InvalidGameException extends RuntimeException {

  public InvalidGameException(String message) {
    super(message);
  }

}
