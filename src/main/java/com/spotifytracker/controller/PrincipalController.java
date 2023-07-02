package com.spotifytracker.controller;

import com.spotifytracker.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/principal")
public class PrincipalController {

    @GetMapping
    public Object principal(Principal principal,
                            Authentication authentication){
        DefaultOAuth2User ud = (DefaultOAuth2User) authentication.getPrincipal();
        return ud.getAttributes().get("display_name");
    }
}
