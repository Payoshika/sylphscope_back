package com.scholarship.scholarship.auth;
import com.scholarship.scholarship.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Value("${frontend.url}")
    private String frontendUrl;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MfaService mfaService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        // Get email
        String email = null;
        Object principal = authentication.getPrincipal();
        if (principal instanceof OAuth2UserImpl oauth2User) {
            email = (String) oauth2User.getAttributes().get("email");
        } else if (principal instanceof OidcUser oidcUser) {
            email = oidcUser.getEmail();
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found after OAuth2 login"));

        // Generate initial JWT without MFA verification
        String jwt = jwtUtils.generateJwtToken(authentication);

        if (user.isMfaEnabled()) {
            // For users with MFA enabled, send verification code
            mfaService.generateAndSendVerificationCode(user);

            // Redirect to MFA verification page with initial token
            String redirectUrl = frontendUrl + "/mfa-verify?token=" + jwt + "&email=" + email;
            getRedirectStrategy().sendRedirect(request, response, redirectUrl);
        } else {
            // For users without MFA, proceed normally
            String redirectUrl = frontendUrl + "/oauth2/redirect?token=" + jwt;
            getRedirectStrategy().sendRedirect(request, response, redirectUrl);
        }
    }
}