package org.s21.tictactoe.domain.service.user;

import org.s21.tictactoe.web.model.SignUpRequest;

import java.util.UUID;

public interface AuthorizationService {

  boolean registerUser(SignUpRequest signUpRequest);
  UUID processAuthorization(String base64LoginPassword);

}
