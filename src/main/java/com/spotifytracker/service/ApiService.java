package com.spotifytracker.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.spotifytracker.model.Album;
import com.spotifytracker.model.Artist;
import com.spotifytracker.model.User;
import com.spotifytracker.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApiService {
    private final RestTemplate restTemplate;
    private final RepositoryService repositoryService;

    @Autowired
    public ApiService(RestTemplate restTemplate,
                      RepositoryService repositoryService) {
        this.restTemplate = restTemplate;
        this.repositoryService = repositoryService;
    }


    public List<Artist> getFollowedArtists(User user) throws IOException {
        String uri = "https://api.spotify.com/v1/me/following?type=artist";
        List<Artist> artists = new ArrayList<>();

        // traverse over all artists from response
        do {
            ResponseEntity<JsonNode> responseEntity = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    null,
                    JsonNode.class
            );

            if(responseEntity.getStatusCode().isError())
                throw new RuntimeException();

            uri = responseEntity.getBody().get("artists").get("next").asText();
            artists.addAll(JsonUtil.extractArtists(responseEntity));
        } while (!uri.equals("null"));

        user.setArtists(artists);
        repositoryService.saveUser(user);
        return artists;
    }

    public List<Album> getAlbums(String id) throws IOException {
        List<Artist> artists = repositoryService.findArtistsByUserId(id);
        List<Album> albums = new ArrayList<>();

        for (Artist artist : artists) {
            List<Album> albumsOfOneArtist = new ArrayList<>();
            String uri = "https://api.spotify.com/v1/artists/" + artist.getId() + "/albums";

            //traverse over all albums api responses
            do {
                ResponseEntity<JsonNode> responseEntity = restTemplate.exchange(
                        uri,
                        HttpMethod.GET,
                        null,
                        JsonNode.class
                );

                if(responseEntity.getStatusCode().isError())
                    throw new RuntimeException();

                uri = responseEntity.getBody().get("next").asText();
                albumsOfOneArtist.addAll(JsonUtil.extractAlbums(responseEntity));
            } while (!uri.equals("null"));

            artist.setRecentAlbums(albumsOfOneArtist);
            repositoryService.saveArtist(artist);
            albums.addAll(albumsOfOneArtist);
        }
        return albums;
    }
}
