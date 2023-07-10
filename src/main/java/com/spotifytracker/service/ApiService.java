package com.spotifytracker.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotifytracker.model.Artist;
import com.spotifytracker.model.Image;
import com.spotifytracker.model.User;
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

    @Autowired
    public ApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Artist> getFollowedArtists(User user) throws IOException {
        String uri = "https://api.spotify.com/v1/me/following?type=artist";
        ResponseEntity<JsonNode> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                JsonNode.class
        );

        //todo check if null & more than 20 objects
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = responseEntity.getBody().get("artists").get("items");

        List<Artist> artists = new ArrayList<>();
        for (JsonNode artistNode : jsonNode) {
            //todo extract
            String id = artistNode.get("id").asText();
            String name = artistNode.get("name").asText();
            int followers = artistNode.get("followers").get("total").asInt();
            List<Image> images = objectMapper.readValue(artistNode.get("images").traverse(), new TypeReference<List<Image>>() {
            });
            Artist artist = new Artist(id, name, followers, images);
            artists.add(artist);
        }

        //TODO save in a repo
        user.setArtists(artists);
        return artists;
    }
}
