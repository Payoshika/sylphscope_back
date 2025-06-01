package com.scholarship.scholarship.auth;

import com.scholarship.scholarship.config.JwtProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    @Autowired
    private JwtProperties jwtProperties;

    public String generateJwtToken(Authentication authentication) {
        String username;

        // Handle different types of authentication principals
        if (authentication.getPrincipal() instanceof UserDetailsImpl userPrincipal) {
            username = userPrincipal.getUsername();
        } else if (authentication.getPrincipal() instanceof OidcUser oidcUser) {
            // For OAuth2/OIDC users, use email for consistency
            username = oidcUser.getEmail();
        } else if (authentication.getPrincipal() instanceof OAuth2UserImpl oauth2User) {
            // Get email from attributes for consistency
            username = (String) oauth2User.getAttributes().get("email");
        } else {
            throw new IllegalArgumentException("Unsupported principal type: " +
                    authentication.getPrincipal().getClass().getName());
        }


        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtProperties.getExpirationMs()))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String getEmailFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("email", String.class);
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Key getSigningKey() {
        byte[] keyBytes = jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}