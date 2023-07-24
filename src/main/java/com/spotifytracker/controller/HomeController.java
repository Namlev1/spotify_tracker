package com.spotifytracker.controller;

import com.spotifytracker.model.User;
import com.spotifytracker.service.BackgroundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/")
public class HomeController {
    private final BackgroundService backgroundService;

    @Autowired
    public HomeController(BackgroundService backgroundService) {
        this.backgroundService = backgroundService;
    }

    @GetMapping
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("login/success")
    public String loginSuccess(Authentication authentication) throws IOException {
        backgroundService.receiveUserSpotifyData((User) authentication.getPrincipal());
        return "login_success";
    }
}