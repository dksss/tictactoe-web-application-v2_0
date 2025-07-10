package org.s21.tictactoe.domain.service.user;

import lombok.RequiredArgsConstructor;
import org.s21.tictactoe.domain.exception.AccessDeniedException;
import org.s21.tictactoe.domain.exception.InvalidUserException;
import org.s21.tictactoe.domain.model.User;
import org.s21.tictactoe.web.model.SignUpRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicAuthService implements AuthorizationService {

  private final UserService userService;
  private final PasswordEncoder passwordEncoder;

  @Override
  public boolean registerUser(SignUpRequest signUpRequest) {
    if (userService.isUserExists(signUpRequest.getLogin())) {
      return false;
    }

    UUID userId = UUID.randomUUID();
    String userLogin = signUpRequest.getLogin();
    String userHashedPassword = passwordEncoder.encode(signUpRequest.getPassword());

    User user = new User(userId, userLogin, userHashedPassword);
    userService.save(user);

    return true;
  }

  @Override
  public UUID processAuthorization(String base64LoginPassword) throws AccessDeniedException, InvalidUserException {
    String[] decodedLoginAndPassword = decodeLoginAndPassword(base64LoginPassword);

    String login = decodedLoginAndPassword[0];
    String rawPassword = decodedLoginAndPassword[1];

    User existUser = userService.getUserByLogin(login);
    if (!passwordEncoder.matches(rawPassword, existUser.getPassword())) {
      throw new AccessDeniedException("Wrong password");
    }

    return existUser.getId();
  }

  private String[] decodeLoginAndPassword(String base64LoginPassword) throws AccessDeniedException {
    if (base64LoginPassword == null || !base64LoginPassword.startsWith("Basic ")) {
      throw new AccessDeniedException("Missing or invalid Authorization header. You must use Basic Auth header");
    }

    String rawData = base64LoginPassword.substring("Basic ".length());
    String decodedData = new String(Base64.getDecoder().decode(rawData));

    return decodedData.split(":", 2);
  }

}
