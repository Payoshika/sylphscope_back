package com.scholarship.scholarship.auth;

import com.scholarship.scholarship.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private MongoUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String username = jwtUtils.getUsernameFromJwtToken(jwt);
                logger.info("Authenticating user from JWT: " + username);
                logger.info("Request URI: " + request.getRequestURI());
                logger.info("Request Method: " + request.getMethod());
                UserDetails userDetails = null;

                // Check if this endpoint requires MFA
                if (requiresMfa(request) && !jwtUtils.isMfaVerified(jwt)) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("{\"message\":\"MFA verification required\"}");
                    return;
                }

                // For OAuth2 users, check by email first
                if (username.contains("@")) {
                    try {
                        userDetails = userDetailsService.loadUserByEmail(username);
                        logger.info("User found by email: " + username);
                    } catch (UsernameNotFoundException e) {
                        logger.info("User not found by email: " + username);
                    }
                }

                // If not found by email, try by username
                if (userDetails == null) {
                    try {
                        userDetails = userDetailsService.loadUserByUsername(username);
                        logger.info("User found by username: " + username);
                    } catch (UsernameNotFoundException e) {
                        logger.warn("Cannot authenticate - user not found: " + username);
                    }
                }

                if (userDetails != null) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    logger.info("User authenticated successfully: " + username);
                } else {
                    logger.error("User not found by username or email: " + username);
                }
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }

    private boolean requiresMfa(HttpServletRequest request) {
        String path = request.getRequestURI();
        // List paths that require MFA verification
        return path.startsWith("/api/admin/") ||
                path.startsWith("/api/issuer/sensitive/") ||
                path.equals("/api/users/profile/update");
    }
}