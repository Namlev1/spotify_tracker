package com.spotifytracker.controller;

import com.spotifytracker.model.Album;
import com.spotifytracker.model.Artist;
import com.spotifytracker.model.User;
import com.spotifytracker.service.ApiService;
import com.spotifytracker.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/releases")
public class ReleasesController {

    private final ApiService apiService;
    private final RepositoryService repositoryService;

    @Autowired
    public ReleasesController(ApiService apiService,
                              RepositoryService repositoryService) {
        this.apiService = apiService;
        this.repositoryService = repositoryService;
    }

    @GetMapping
    String getArtists(Authentication authentication,
                      ModelMap model) throws IOException {
        //todo change, so that it refreshes one's account on login in the background
        apiService.refreshArtistsAndAlbums();

        User user = (User) authentication.getPrincipal();
        List<Artist> artists = repositoryService.findArtistsByUserId(user.getId());
        List<Album> albums = new ArrayList<>();
        model.addAttribute("artists", artists);
        return "releases";
    }
}
