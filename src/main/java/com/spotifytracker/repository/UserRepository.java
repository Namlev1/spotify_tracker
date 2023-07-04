package com.spotifytracker.repository;

import com.spotifytracker.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByDisplayName(String displayName);
}
