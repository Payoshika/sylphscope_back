package com.scholarship.scholarship.auth;

import com.scholarship.scholarship.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class OAuth2UserService extends DefaultOAuth2UserService {
    private static final Logger logger = LoggerFactory.getLogger(OAuth2UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public OAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        logger.info("OAuth2UserService initialized");
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        logger.info("OAuth2UserService.loadUser() called");
        try {
            OAuth2User oAuth2User = super.loadUser(userRequest);

            // Extract provider details
            String provider = userRequest.getClientRegistration().getRegistrationId();
            String providerId = oAuth2User.getAttribute("sub"); // For Google
            String email = oAuth2User.getAttribute("email");
            String name = oAuth2User.getAttribute("name");

            logger.info("OAuth2 user data loaded: provider=" + provider +
                    ", email=" + email + ", name=" + name);

            // Find or create user in database
            User user = userRepository.findByEmail(email)
                    .orElseGet(() -> {
                        try {
                            User newUser = new User();
                            newUser.setUsername(name != null ? name : email);
                            newUser.setEmail(email);
                            newUser.setPassword(UUID.randomUUID().toString());
                            newUser.setEnabled(true);
                            newUser.setRoles(List.of(Role.TEMPORARY));
                            newUser.setGoogleId(providerId);

                            System.out.println("Creating new OAuth2 user: " + name);
                            User savedUser = userRepository.save(newUser);
                            System.out.println("User created with ID: " + savedUser.getId());
                            return savedUser;
                        } catch (Exception e) {
                            System.err.println("Failed to create user in database: " + e.getMessage());
                            e.printStackTrace();
                            // Return a temporary user object
                            User tempUser = new User();
                            tempUser.setUsername(name != null ? name : email);
                            tempUser.setEmail(email);
                            tempUser.setRoles(List.of(Role.TEMPORARY));
                            return tempUser;
                        }
                    });

            return new OAuth2UserImpl(user, oAuth2User.getAttributes());
        } catch (Exception e) {
            System.err.println("Error in OAuth2UserService.loadUser: " + e.getMessage());
            e.printStackTrace();
            throw new OAuth2AuthenticationException(e.getMessage());
        }
    }
}