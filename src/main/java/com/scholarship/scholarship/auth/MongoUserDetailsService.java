package com.scholarship.scholarship.auth;

import com.scholarship.scholarship.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MongoUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // Load user by username or email
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        // If not found by username, try by email
        if (user == null) {
            user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException(
                            "User not found with username/email: " + username));
        }
        return new UserDetailsImpl(user);
    }
    // loadUserByEmail method to load user by email for Google SSO or other email-based logins
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found by email: " + email));
        return new UserDetailsImpl(user);
    }

    //Google SSO, will used to load user by Google ID
    public UserDetails loadUserByGoogleId(String googleId) {
        User user = userRepository.findByGoogleId(googleId)
                .orElseThrow(() -> new UsernameNotFoundException("Google user not found"));
        return new UserDetailsImpl(user);
    }
}