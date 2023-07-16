package com.spotifytracker.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotifytracker.model.Album;
import com.spotifytracker.model.Artist;
import com.spotifytracker.model.Image;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
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

    public static List<Album> extractAlbums(ResponseEntity<JsonNode> response) throws IOException {
        // TODO work with "next" parameters

        List<Album> albums = new ArrayList<>();
        JsonNode node = response.getBody().get("items");
        for (JsonNode albumNode : node) {
            // if release date is not precise, skip
            if (!albumNode.get("release_date_precision").asText().equals("day"))
                continue;

            LocalDate date = LocalDate.parse(albumNode.get("release_date").asText());
            // if album is not recent enough, skip
            Period period = Period.between(date, LocalDate.now());
            if (period.getYears() > 0
                    || period.getMonths() > 0
                    || period.getDays() > 30) {
                continue;
            }

            String type = albumNode.get("album_type").asText();
            // if album is part of a compilation, skip
            if (type.equals("compilation"))
                continue;

            String id = albumNode.get("id").asText();
            String name = albumNode.get("name").asText();
            String uri = albumNode.get("external_urls").get("spotify").asText();
            List<Image> images = objectMapper.readValue(albumNode.get("images").traverse(), new TypeReference<>() {
            });

            Album album = new Album(id, name, date, uri, type, images);
            albums.add(album);
        }
        return albums;
    }
}
