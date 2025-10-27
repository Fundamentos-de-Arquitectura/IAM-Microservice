package com.go5u.foodflowplatform.foodflowplatform.iam.infrastructure.tokens.jwt;


import com.go5u.foodflowplatform.foodflowplatform.iam.application.internal.outboundservices.tokens.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface BearerTokenService extends TokenService {
    String getBearerTokenFrom(HttpServletRequest token);

    String generateToken(Authentication authentication);
}
