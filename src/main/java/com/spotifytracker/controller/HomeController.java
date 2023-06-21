package com.spotifytracker.controller;

import com.spotifytracker.service.SpotifyAccessTokenProvider;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class HomeController {
    private SpotifyAccessTokenProvider tokenExchange;

    @Autowired
    public HomeController(SpotifyAccessTokenProvider tokenExchange){
        this.tokenExchange = tokenExchange;
    }

    @GetMapping("/callback")
    public String callback(HttpServletRequest request) {
        String authorizationCode = request.getParameter("code");
//        System.out.println(authorizationCode);
        return tokenExchange.exchangeAuthorizationCodeForAccessToken(authorizationCode);
    }


}