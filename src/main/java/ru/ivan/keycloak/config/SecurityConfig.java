package ru.ivan.keycloak.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.ivan.keycloak.model.Role;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http.authorizeHttpRequests(authz -> authz
        .requestMatchers(HttpMethod.PUT, "/api/**").hasAuthority(Role.MODERATOR.name())
        .requestMatchers(HttpMethod.POST, "/api/**").hasAuthority(Role.MODERATOR.name())
        .requestMatchers(HttpMethod.DELETE, "/api/**").hasAuthority(Role.MODERATOR.name())
        .requestMatchers("/api/**").authenticated()
        .anyRequest().permitAll()
    );
    http.oauth2ResourceServer()
        .jwt()
        .jwtAuthenticationConverter(new CustomJwtAuthenticationConverter());
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.cors();
    http.csrf().disable();
    return http.build();
  }

  @Bean
  public JwtDecoder jwtDecoder(OAuth2ResourceServerProperties oAuth2ResourceServerProperties) {
    NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(
        oAuth2ResourceServerProperties.getJwt().getJwkSetUri()).build();
    jwtDecoder.setJwtValidator(
        JwtValidators.createDefaultWithIssuer(
            oAuth2ResourceServerProperties.getJwt().getIssuerUri()));
    return jwtDecoder;
  }
}
