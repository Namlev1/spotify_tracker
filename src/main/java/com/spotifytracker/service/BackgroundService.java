package com.spotifytracker.service;

import com.spotifytracker.model.Artist;
import com.spotifytracker.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class BackgroundService {
    private final ApiService apiService;

    @Autowired
    public BackgroundService(ApiService apiService) {
        this.apiService = apiService;
    }

    @Async
    public CompletableFuture<User> receiveUserSpotifyData(User user) throws IOException {
        List<Artist> artists = apiService.requestFollowedArtists(user);
        for (Artist artist : artists)
            apiService.requestAlbums(artist);
        return CompletableFuture.completedFuture(user);
    }
}