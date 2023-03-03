package ru.ivan.keycloak.annotation;

import java.util.Arrays;
import java.util.Optional;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Aspect
@Component
public class RolesAspect {

  @Before("@annotation(ru.ivan.keycloak.annotation.AllowedRoles)")
  public void before(JoinPoint joinPoint) {

    var expectedRoles = Arrays.stream(((MethodSignature) joinPoint.getSignature()).getMethod()
        .getAnnotation(AllowedRoles.class).value()).map(Enum::name).toList();

    var grantedAuthorities =
        Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
            .map(Authentication::getAuthorities)
            .orElseThrow(() -> new AccessDeniedException("No authorities found"));

    var roles = grantedAuthorities.stream()
        .map(GrantedAuthority::getAuthority)
        .toList();

    if (!CollectionUtils.containsAny(expectedRoles, roles)) {
      throw new AccessDeniedException(
          String.format("Unauthorized request. Expected to have %s roles, but have %s",
              expectedRoles, roles));
    }
  }
}
