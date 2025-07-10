package org.s21.tictactoe.web.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.s21.tictactoe.domain.exception.AccessDeniedException;
import org.s21.tictactoe.domain.exception.InvalidUserException;
import org.s21.tictactoe.domain.service.user.AuthorizationService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class AuthFilter extends GenericFilterBean {

  private final AuthorizationService authorizationService;

  @Override
  public void doFilter(ServletRequest servletRequest,
                       ServletResponse servletResponse,
                       FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    String path = request.getRequestURI();

    if (!path.equals("/login") && !path.equals("/registration")) {
      try {
        String authData = request.getHeader(HttpHeaders.AUTHORIZATION);
        UUID userId = authorizationService.processAuthorization(authData);

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
          Authentication newAuth = new UsernamePasswordAuthenticationToken(
                  userId,
                  "user",
                  List.of(new SimpleGrantedAuthority("ROLE_USER")));

          SecurityContextHolder.getContext().setAuthentication(newAuth);
        }
      } catch (AccessDeniedException | InvalidUserException ex) {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return;
      }
    }

    filterChain.doFilter(servletRequest, servletResponse);
  }
}
