package ru.ivan.keycloak.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import ru.ivan.keycloak.AccessToken;
import ru.ivan.keycloak.JwtTokenValidator;
import ru.ivan.keycloak.error.InvalidTokenException;

@Slf4j
class AccessTokenFilter extends AbstractAuthenticationProcessingFilter {

  private final JwtTokenValidator tokenVerifier;

  public AccessTokenFilter(
      JwtTokenValidator jwtTokenValidator,
      AuthenticationManager authenticationManager,
      AuthenticationFailureHandler authenticationFailureHandler) {

    super(AnyRequestMatcher.INSTANCE);
    setAuthenticationManager(authenticationManager);
    setAuthenticationFailureHandler(authenticationFailureHandler);
    this.tokenVerifier = jwtTokenValidator;
  }

  private String extractAuthorizationHeaderAsString(
      jakarta.servlet.http.HttpServletRequest request) {
    try {
      return request.getHeader("Authorization");
    } catch (Exception ex) {
      throw new InvalidTokenException("There is no Authorization header in a request", ex);
    }
  }

  @Override
  public Authentication attemptAuthentication(jakarta.servlet.http.HttpServletRequest request,
      jakarta.servlet.http.HttpServletResponse response)
      throws AuthenticationException {
    log.info("Attempting to authenticate for a request {}", request.getRequestURI());

    String authorizationHeader = extractAuthorizationHeaderAsString(request);
    AccessToken accessToken = tokenVerifier.validateAuthorizationHeader(authorizationHeader);
    return this.getAuthenticationManager()
        .authenticate(new JwtAuthentication(accessToken));
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authResult) throws IOException, ServletException {
    log.info("Successfully authentication for the request {}", request.getRequestURI());

    SecurityContextHolder.getContext().setAuthentication(authResult);
    chain.doFilter(request, response);
  }
}
