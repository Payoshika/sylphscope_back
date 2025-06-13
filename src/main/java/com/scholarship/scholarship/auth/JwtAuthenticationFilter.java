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
import java.util.Calendar;
import java.util.Date;

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
                String userId = jwtUtils.getUserIdFromJwtToken(jwt);
                String username = jwtUtils.getUsernameFromJwtToken(jwt);

                // Skip MFA check for authentication endpoints
                if (isAuthEndpoint(request)) {
                    filterChain.doFilter(request, response);
                    return;
                }

                // Load user details by ID
                UserDetails userDetails = null;
                User user = null;

                try {
                    user = userRepository.findById(userId).orElse(null);
                    if (user != null) {
                        userDetails = new UserDetailsImpl(user);
                    }
                } catch (Exception e) {
                    logger.warn("Cannot authenticate - user not found by ID: " + userId);
                }

                if (userDetails != null && user != null) {
                    // Check if MFA is needed
                    if (needsMfaVerification(user) && !jwtUtils.isMfaVerified(jwt)) {
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        response.getWriter().write("{\"message\":\"MFA verification required\",\"requireMfa\":true}");
                        return;
                    }
                    // Process authentication
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    jwt, // Store the JWT as credentials
                                    userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }
        filterChain.doFilter(request, response);
    }

    private boolean needsMfaVerification(User user) {
        if (!user.isMfaEnabled()) {
            return false;
        }
        Date lastVerified = user.getLastMfaVerifiedDate();
        // If never verified with MFA, verification is needed
        if (lastVerified == null) {
            return true;
        }
        // Check if last verification was more than a month ago
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1); // One month ago
        Date oneMonthAgo = cal.getTime();
        return lastVerified.before(oneMonthAgo);
    }

    private boolean isAuthEndpoint(HttpServletRequest request) {
        String path = request.getRequestURI();
        // Exclude auth endpoints from MFA check
        return path.contains("/api/public/") ||
                path.contains("/login") ||
                path.contains("/mfa/");
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }
}