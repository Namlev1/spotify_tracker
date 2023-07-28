package com.spotifytracker.repository;

import com.spotifytracker.model.Artist;
import com.spotifytracker.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ArtistRepositoryTest {
    @Autowired
    ArtistRepository artistRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    void findArtistsGivenUserId(){
        User user = new User();
        Artist testArtist = new Artist();
        testArtist.setId("testId");
        user.setId("id");
        user.setArtists(List.of(testArtist));

        userRepository.save(user);
        List<Artist> retreivedArtists = artistRepository.findByUsersId("id").get();
        Artist artist = retreivedArtists.get(0);
        assertEquals("testId", artist.getId());
    }
}