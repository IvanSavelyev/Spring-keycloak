package ru.ivan.keycloak.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class AuthorizationAccessDeniedHandler implements AccessDeniedHandler {

  private String createErrorBody(AccessDeniedException exception) {
    JsonObject exceptionMessage = new JsonObject();
    exceptionMessage.addProperty("code", HttpStatus.FORBIDDEN.value());
    exceptionMessage.addProperty("reason", HttpStatus.FORBIDDEN.getReasonPhrase());
    exceptionMessage.addProperty("timestamp", Instant.now().toString());
    exceptionMessage.addProperty("message", exception.getMessage());
    return new Gson().toJson(exceptionMessage);
  }

  @Override
  public void handle(jakarta.servlet.http.HttpServletRequest request,
      jakarta.servlet.http.HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException {
    response.setStatus(HttpStatus.FORBIDDEN.value());
    response.setContentType("application/json;charset=UTF-8");
    response.getWriter().write(createErrorBody(accessDeniedException));
  }
}
