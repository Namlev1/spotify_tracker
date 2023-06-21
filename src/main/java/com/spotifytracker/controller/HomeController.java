package com.spotifytracker.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;

@RestController
public class HomeController {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    @GetMapping("/callback")
    public String callback(HttpServletRequest request){
        String authorizationCode = request.getParameter("code");
        return authorizationCode;
    }

    @GetMapping("/user")
    public String user(Principal principal){
        return "home";
    }
}