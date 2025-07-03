package com.scholarship.scholarship.auth;

import com.scholarship.scholarship.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomOidcUserService extends OidcUserService {
    private static final Logger logger = LoggerFactory.getLogger(CustomOidcUserService.class);

    private final UserRepository userRepository;

    @Autowired
    public CustomOidcUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        logger.info("CustomOidcUserService initialized");
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        logger.info("CustomOidcUserService.loadUser() called");

        OidcUser oidcUser = super.loadUser(userRequest);

        try {
            // Extract user details
            String email = oidcUser.getEmail();
            String name = oidcUser.getFullName();
            String googleId = oidcUser.getSubject();

            logger.info("OIDC user data loaded: email={}, name={}", email, name);

            // Find or create user in database
            User user = userRepository.findByEmail(email)
                    .orElseGet(() -> {
                        User newUser = new User();
                        newUser.setUsername(name != null ? name : email);
                        newUser.setEmail(email);
                        newUser.setPassword(UUID.randomUUID().toString());
                        newUser.setEnabled(true);
                        newUser.setRoles(List.of(Role.STUDENT));
                        newUser.setGoogleId(googleId);

                        logger.info("Creating new OIDC user: {}", name);
                        return userRepository.save(newUser);
                    });

            return new CustomOidcUser(oidcUser, user);
        } catch (Exception e) {
            logger.error("Error in CustomOidcUserService.loadUser", e);
            throw new OAuth2AuthenticationException(e.getMessage());
        }
    }
}
