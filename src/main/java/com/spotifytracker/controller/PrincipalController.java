package com.spotifytracker.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/principal")
public class PrincipalController {

    @GetMapping
    public Object principal(Authentication authentication) {
        return authentication.getPrincipal();
    }
}
