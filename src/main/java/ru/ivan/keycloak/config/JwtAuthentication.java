package ru.ivan.keycloak.config;

import lombok.ToString;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import ru.ivan.keycloak.AccessToken;

@ToString
public class JwtAuthentication extends AbstractAuthenticationToken {

  private final AccessToken accessToken;

  public JwtAuthentication(AccessToken accessToken) {
    super(accessToken.getAuthorities());
    this.accessToken = accessToken;
  }

  @Override
  public Object getCredentials() {
    return accessToken.getValue();
  }

  @Override
  public Object getPrincipal() {
    return accessToken.getUsername();
  }
}
