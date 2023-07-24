package com.spotifytracker.controller;

import com.spotifytracker.model.Artist;
import com.spotifytracker.model.User;
import com.spotifytracker.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/releases")
public class ReleasesController {

    private final RepositoryService repositoryService;

    @Autowired
    public ReleasesController(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @GetMapping
    String getArtists(Authentication authentication,
                      ModelMap model) {

        User user = (User) authentication.getPrincipal();
        List<Artist> artists = repositoryService.findArtistsByUserId(user.getId());
        model.addAttribute("artists", artists);
        return "releases";
    }
}
