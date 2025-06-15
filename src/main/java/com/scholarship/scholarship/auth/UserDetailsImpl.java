package com.scholarship.scholarship.auth;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class UserDetailsImpl implements UserDetails {
    private String id;
    private String username;
    private String email;
    private String password;
    private boolean enabled;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.enabled = user.isEnabled();
        this.authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                .collect(Collectors.toList());
    }
}