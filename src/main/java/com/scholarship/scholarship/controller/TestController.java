package com.scholarship.scholarship.controller;

import com.scholarship.scholarship.auth.OAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class TestController {

    @Autowired
    private OAuth2UserService oAuth2UserService;

    @GetMapping("/test-oauth2-service")
    public String testOAuth2Service() {
        return "OAuth2UserService is properly injected";
    }
}