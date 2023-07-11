package com.spotifytracker.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotifytracker.model.Artist;
import com.spotifytracker.model.Image;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<Artist> extractArtists(ResponseEntity<JsonNode> response) throws IOException {
        /* TODO handle:
         *  1. 0 followers
         *  2. 1 follower
         *  3. 20+ followers
         */
        JsonNode node = response.getBody().get("artists").get("items");

        List<Artist> artists = new ArrayList<>();
        for (JsonNode artistNode : node) {
            String id = artistNode.get("id").asText();
            String name = artistNode.get("name").asText();
            int followers = artistNode.get("followers").get("total").asInt();
            List<Image> images = objectMapper.readValue(artistNode.get("images").traverse(), new TypeReference<>() {
            });
            Artist artist = new Artist(id, name, followers, images);
            artists.add(artist);
        }

        return artists;
    }
}
