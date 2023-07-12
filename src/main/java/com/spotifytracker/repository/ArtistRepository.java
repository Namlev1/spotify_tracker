package com.spotifytracker.repository;

import com.spotifytracker.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, String> {
    Optional<List<Artist>> findByUsersId(String userId);

}
