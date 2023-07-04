package com.spotifytracker.config;

import com.spotifytracker.model.Image;
import com.spotifytracker.model.User;
import com.spotifytracker.repository.UserRepository;
import com.spotifytracker.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import static //
        org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private RepositoryService repoService;

    @Autowired
    public SecurityConfig(RepositoryService repoService) {
        this.repoService = repoService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth->{
                    auth.requestMatchers("/", "/home").permitAll();
                    auth.requestMatchers(toH2Console()).permitAll();
                    auth.anyRequest().authenticated();
                })
                .oauth2Login(oauth2 -> {
                    oauth2
                            .userInfoEndpoint()
                            .userService(customOAuth2UserService());
                })
                .csrf(csrf -> csrf.ignoringRequestMatchers(toH2Console()))
                .headers(head -> head.frameOptions().disable())
                .build();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> customOAuth2UserService() {
        return userRequest -> {
            DefaultOAuth2UserService defaultOAuth2UserService = new DefaultOAuth2UserService();
            OAuth2User oauth2User = defaultOAuth2UserService.loadUser(userRequest);// Obtain the OAuth2User from userRequest

            // Extract necessary user details
            String id = oauth2User.getName();// Extract the username from the OAuth2User
            Map<String, Object> attributes = oauth2User.getAttributes();
            Optional<User> optional = repoService.findUserById(id);
            User user = null;
            if (optional.isEmpty()){
                user = new User();
                //TODO extract function
                user.setId(id);
                user.setDisplayName((String) attributes.get("display_name"));
                user.setType((String) attributes.get("type"));
                user.setHref((String) attributes.get("href"));
                user.setUri((String) attributes.get("uri"));

                Map<String, Object> followers = (Map<String, Object>) attributes.get("followers");
                user.setFollowers((Integer) followers.get("total"));

                // Parse images
                List<LinkedHashMap<String, Object>> imageMaps = (List<LinkedHashMap<String, Object>>) attributes.get("images");
                List<Image> images = new ArrayList<>();
                for(LinkedHashMap<String, Object> imageMap : imageMaps){
                    Image image = new Image();
                    image.setUrl((String) imageMap.get("url"));
                    image.setWidth((Integer) imageMap.get("width"));
                    image.setHeight((Integer) imageMap.get("height"));
                    images.add(image);
                }
                user.setImages(images);

                repoService.saveUser(user);


            } else {
                user = optional.get();
            }

            return user;
        };
    }


    @Bean
    public UserDetailsService userDetailsService(UserRepository repository){
        return username -> {
            User user = repository.findByDisplayName(username);
            if (user != null)
                return user;
            throw new UsernameNotFoundException("nie ma");
        };
    }
}