package com.spotifytracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/releases")
public class ReleasesController {

    @GetMapping
    public String recentReleases(){
        return "releases";
    }
}
