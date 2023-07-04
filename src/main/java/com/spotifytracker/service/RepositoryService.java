package com.spotifytracker.service;

import com.spotifytracker.model.Image;
import com.spotifytracker.model.User;
import com.spotifytracker.repository.ImageRepository;
import com.spotifytracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RepositoryService {
    private UserRepository userRepository;
    private final ImageRepository imageRepository;

    @Autowired
    public RepositoryService(UserRepository userRepository, ImageRepository imageRepository) {
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

    //TODO implement
    public User registerUser(DefaultOAuth2User oAuth2User){
        User user = new User();
        return null;
    }

    public Optional<User> findUserById(String id){
        return userRepository.findById(id);
    }

    public Image saveImage(Image image){
        return imageRepository.save(image);
    }

}
