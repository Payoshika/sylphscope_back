package com.scholarship.scholarship.auth;

import com.scholarship.scholarship.dto.JwtResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Value("${frontend.url}")
    private String frontendUrl;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // Generate JWT token
        String jwt = jwtUtils.generateJwtToken(authentication);

        // Extract user details and roles
        String userId;
        String username;
        List<String> roles;

        Object principal = authentication.getPrincipal();

        if (principal instanceof OAuth2UserImpl oauth2User) {
            userId = oauth2User.getId();
            username = oauth2User.getName();
            roles = oauth2User.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
        } else if (principal instanceof OidcUser oidcUser) {
            // For Google OAuth2/OIDC
            userId = oidcUser.getSubject();  // Use subject as ID
            username = oidcUser.getAttribute("name");
            if (username == null) {
                username = oidcUser.getEmail();
            }
            roles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("Unsupported principal type: " +
                    principal.getClass().getName());
        }
        // Create response
        JwtResponse jwtResponse = new JwtResponse(
                jwt,
                userId,
                username,
                roles);
        // Redirect to frontend with token
        String redirectUrl = frontendUrl + "/oauth2/redirect?token=" + jwt;
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}