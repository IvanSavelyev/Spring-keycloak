package ru.ivan.keycloak.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

class AccessTokenAuthenticationFailureHandler implements AuthenticationFailureHandler {

  private String createErrorBody(AuthenticationException exception) {
    JsonObject exceptionMessage = new JsonObject();
    exceptionMessage.addProperty("code", HttpStatus.UNAUTHORIZED.value());
    exceptionMessage.addProperty("reason", HttpStatus.UNAUTHORIZED.getReasonPhrase());
    exceptionMessage.addProperty("timestamp", Instant.now().toString());
    exceptionMessage.addProperty("message", exception.getMessage());
    return new Gson().toJson(exceptionMessage);
  }

  @Override
  public void onAuthenticationFailure(jakarta.servlet.http.HttpServletRequest request,
      jakarta.servlet.http.HttpServletResponse response, AuthenticationException exception)
      throws IOException {
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setContentType("application/json;charset=UTF-8");
    response.getWriter().write(createErrorBody(exception));
  }
}
