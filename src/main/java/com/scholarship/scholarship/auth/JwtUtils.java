package com.scholarship.scholarship.auth;

import com.scholarship.scholarship.config.JwtProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;
import java.util.Calendar;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import com.scholarship.scholarship.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Component
public class JwtUtils {

    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private UserRepository userRepository;

    // In JwtUtils.java
    public String generateJwtToken(Authentication authentication) {
        String userId;
        String username;

        // Extract both user ID and username from different authentication principals
        if (authentication.getPrincipal() instanceof UserDetailsImpl userPrincipal) {
            userId = userPrincipal.getId();
            username = userPrincipal.getUsername();
        } else if (authentication.getPrincipal() instanceof OidcUser oidcUser) {
            // For Google authed OAuth2/OIDC users, find by email
            User user = userRepository.findByEmail(oidcUser.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            userId = user.getId();  // Use the actual MongoDB ID
            username = oidcUser.getEmail();
        } else if (authentication.getPrincipal() instanceof OAuth2UserImpl oauth2User) {
            // Get MongoDB ID for the user
            User user = userRepository.findByEmail((String) oauth2User.getAttributes().get("email"))
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            userId = user.getId();  // Use the actual MongoDB ID
            username = (String) oauth2User.getAttributes().get("email");
        } else {
            throw new IllegalArgumentException("Unsupported principal type: " +
                    authentication.getPrincipal().getClass().getName());
        }

        return Jwts.builder()
                .setSubject(userId)
                .claim("username", username)  // Store username as a claim
                .claim("mfa_verified", false) // Explicitly mark as not MFA verified initially, later updated by users
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtProperties.getExpirationMs()))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // get user ID from token
    public String getUserIdFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // get username from token claims
    public String getUsernameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("username", String.class);
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

    public String generateMfaVerifiedToken(String originalToken) {
        // Extract user ID and username from original token
        String userId = getUserIdFromJwtToken(originalToken);
        String username = getUsernameFromJwtToken(originalToken);
        Date now = new Date();

        return Jwts.builder()
                .setSubject(userId)  // Use userId as subject, consistent with generateJwtToken
                .claim("username", username)  // Include username as claim
                .claim("mfa_verified", true)
                .claim("mfa_verified_at", now)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + jwtProperties.getExpirationMs()))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    public boolean isMfaVerified(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            // Check if mfa_verified flag exists and is true
            Boolean mfaVerified = claims.get("mfa_verified", Boolean.class);
            if (mfaVerified == null || !mfaVerified) {
                return false;
            }

            // Get verification timestamp if available
            Date mfaVerifiedAt = claims.get("mfa_verified_at", Date.class);
            if (mfaVerifiedAt == null) {
                return true; // For backward compatibility with old tokens
            }

            // Check if MFA verification is older than one month
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -1);
            Date oneMonthAgo = cal.getTime();

            return !mfaVerifiedAt.before(oneMonthAgo);
        } catch (Exception e) {
            return false;
        }
    }
}