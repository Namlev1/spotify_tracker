package com.spotifytracker.service;

import com.spotifytracker.model.User;
import com.spotifytracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(DefaultOAuth2User oAuth2User){
        User user = new User();
        return null;
    }
}
