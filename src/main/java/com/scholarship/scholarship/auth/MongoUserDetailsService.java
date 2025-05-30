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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return new UserDetailsImpl(user);
    }

    //Google SSO
    public UserDetails loadUserByGoogleId(String googleId) {
        User user = userRepository.findByGoogleId(googleId)
                .orElseThrow(() -> new UsernameNotFoundException("Google user not found"));
        return new UserDetailsImpl(user);
    }
}