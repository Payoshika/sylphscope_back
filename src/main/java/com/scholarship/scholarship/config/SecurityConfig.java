package com.scholarship.scholarship.config;

import com.scholarship.scholarship.auth.*;
import com.scholarship.scholarship.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private MongoUserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public OAuth2UserService oAuth2UserService(UserRepository userRepository) {
        return new OAuth2UserService(userRepository);
    }

    @Bean
    public OidcUserService oidcUserService(UserRepository userRepository) {
        return new CustomOidcUserService(userRepository);
    }

    @Autowired
    private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, UserRepository userRepository) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/public/**").permitAll()
                        //this is for testing creating question
                        .requestMatchers("/api/questions/**").permitAll()
                        .requestMatchers("/api/option-sets/**").permitAll()
                        .requestMatchers("/api/eligibility-criteria/**").permitAll()
                        .requestMatchers("/api/question-groups/**").permitAll()
                        .requestMatchers("/api/grant-programs/**").permitAll()
                        .requestMatchers("/api/questions/**").permitAll()
                        .requestMatchers("/api/eligibility-criteria/**").permitAll()
                        .requestMatchers("/api/selection-criteria/**").permitAll()
                        .requestMatchers("/api/providers/**").permitAll()
                        .requestMatchers("/api/provider-staff/**").permitAll()

                        .requestMatchers("/oauth2/**", "/login/oauth2/**").permitAll()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/provider/**").hasRole("PROVIDER")
                        .requestMatchers("/api/student/**").hasRole("STUDENT")
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        //send the user to the authorization endpoint(e.g., Google, GitHub)
                        .authorizationEndpoint(authorization -> authorization
                                .baseUri("/oauth2/authorization"))
                        //receiving the authorization code from the authorization server
                        .redirectionEndpoint(redirection -> redirection
                                .baseUri("/login/oauth2/code/*"))
                        //saving the user information from the authorization server
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oAuth2UserService(userRepository))
                                .oidcUserService(oidcUserService(userRepository)))
                        //handling the success and failure of the OAuth2 login and returning tokens to the client
                        .successHandler(oAuth2AuthenticationSuccessHandler)
                        .failureUrl("/login?error=oauth2")
                );
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}