package com.scholarship.scholarship.config;

import com.scholarship.scholarship.auth.*;
import com.scholarship.scholarship.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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
import static org.springframework.security.config.Customizer.withDefaults;

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
                .cors(withDefaults())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception
                    .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized"))
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/api/public/**").permitAll()
                        .requestMatchers("/api/contact/**").permitAll()
                        .requestMatchers("/api/questions/**").permitAll()
                        .requestMatchers("/api/option-sets/**").permitAll()
                        .requestMatchers("/api/question-groups/**").permitAll()
                        .requestMatchers("/api/grant-programs/**").permitAll()
                        // Only GET requests for students, all requests for providers
                        .requestMatchers(HttpMethod.GET, "/api/questions/**").hasAnyRole("STUDENT", "PROVIDER")
                        .requestMatchers("/api/questions/**").hasRole("PROVIDER")
                        .requestMatchers(HttpMethod.GET, "/api/option-sets/**").hasAnyRole("STUDENT", "PROVIDER")
                        .requestMatchers("/api/option-sets/**").hasRole("PROVIDER")
                        .requestMatchers(HttpMethod.GET, "/api/eligibility-criteria/**").hasAnyRole("STUDENT", "PROVIDER")
                        .requestMatchers("/api/eligibility-criteria/**").hasRole("PROVIDER")
                        .requestMatchers("/api/eligibility-result/**").hasAnyRole("STUDENT", "PROVIDER")
                        .requestMatchers(HttpMethod.GET, "/api/question-groups/**").hasAnyRole("STUDENT", "PROVIDER")
                        .requestMatchers("/api/question-groups/**").hasRole("PROVIDER")
                        .requestMatchers(HttpMethod.GET, "/api/grant-programs/**").hasAnyRole("STUDENT", "PROVIDER")
                        .requestMatchers("/api/grant-programs/**").hasRole("PROVIDER")
                        .requestMatchers(HttpMethod.GET, "/api/selection-criteria/**").hasAnyRole("STUDENT", "PROVIDER")
                        .requestMatchers("/api/selection-criteria/**").hasRole("PROVIDER")
                        .requestMatchers(HttpMethod.GET, "/api/evaluation-of-answers/**").hasAnyRole("STUDENT", "PROVIDER")
                        .requestMatchers("/api/evaluation-of-answers/**").hasRole("PROVIDER")
                        .requestMatchers(HttpMethod.GET, "/api/providers/**").hasAnyRole("STUDENT", "PROVIDER")
                        .requestMatchers("/api/providers/**").hasRole("PROVIDER")
                        .requestMatchers("/api/provider-staff/**").hasRole("PROVIDER")
                        .requestMatchers(HttpMethod.GET, "/api/applications/**").hasAnyRole("PROVIDER", "STUDENT")
                        .requestMatchers("/api/applications/**").hasAnyRole("PROVIDER", "STUDENT")
                        .requestMatchers(HttpMethod.GET, "/api/students/**").hasAnyRole("PROVIDER", "STUDENT")
                        .requestMatchers("/api/students/**").hasRole("STUDENT")
                        .requestMatchers(HttpMethod.GET, "/api/student-answers/**").hasAnyRole("PROVIDER", "STUDENT")
                        .requestMatchers("/api/student-answers/**").hasRole("STUDENT")
                        .requestMatchers("/oauth2/**", "/login/oauth2/**").hasAnyRole("STUDENT", "PROVIDER")
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/provider/**").hasRole("PROVIDER")
                        .requestMatchers("/api/student/**").hasAnyRole("STUDENT", "PROVIDER")
                        .requestMatchers("/api/messages/**").hasAnyRole("STUDENT", "PROVIDER")

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