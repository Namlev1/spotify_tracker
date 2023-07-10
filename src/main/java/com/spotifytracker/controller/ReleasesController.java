package com.spotifytracker.controller;

import com.spotifytracker.model.Artist;
import com.spotifytracker.model.User;
import com.spotifytracker.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/releases")
public class ReleasesController {

    private final ApiService apiService;

    @Autowired
    public ReleasesController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping
    List<Artist> useOauthWithAuthCode(Authentication authentication) throws IOException {
        User user = (User) authentication.getPrincipal();
        return apiService.getFollowedArtists(user);
    }
}
