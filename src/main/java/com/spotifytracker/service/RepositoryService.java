package com.spotifytracker.service;

import com.spotifytracker.model.Image;
import com.spotifytracker.model.User;
import com.spotifytracker.repository.ImageRepository;
import com.spotifytracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RepositoryService {
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    @Autowired
    public RepositoryService(UserRepository userRepository, ImageRepository imageRepository) {
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
    }

    public User registerUser(Map<String, Object> attributes) {
        User user = new User();
        user.setId((String) attributes.get("id"));
        user.setDisplayName((String) attributes.get("display_name"));

        // Parse images
        List<Image> images = parseImages(attributes);
        user.setImages(images);

        return saveUser(user);


    }

    private List<Image> parseImages(Map<String, Object> attributes) {
        List<Image> images = new ArrayList<>();
        List<LinkedHashMap<String, Object>> imageMaps = (List<LinkedHashMap<String, Object>>) attributes.get("images");
        for (LinkedHashMap<String, Object> imageMap : imageMaps) {
            Image image = new Image();
            image.setUrl((String) imageMap.get("url"));
            image.setWidth((Integer) imageMap.get("width"));
            image.setHeight((Integer) imageMap.get("height"));
            images.add(image);
        }
        return images;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findUserById(String id) {
        return userRepository.findById(id);
    }

    public Image saveImage(Image image) {
        return imageRepository.save(image);
    }

}
