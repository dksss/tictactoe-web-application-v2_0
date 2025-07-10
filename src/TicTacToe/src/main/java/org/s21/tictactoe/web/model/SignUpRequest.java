package org.s21.tictactoe.web.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {

  @NotBlank(message = "Login cannot be empty")
  private String login;

  @NotBlank(message = "Password cannot be empty")
  private String password;

}
