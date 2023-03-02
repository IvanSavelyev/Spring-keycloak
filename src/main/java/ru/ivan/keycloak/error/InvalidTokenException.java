package ru.ivan.keycloak.error;

import org.springframework.security.core.AuthenticationException;

public class InvalidTokenException extends AuthenticationException {

  public InvalidTokenException(String message) {
    super(message);
  }

  public InvalidTokenException(String message, Throwable cause) {
    super(message, cause);
  }
}
