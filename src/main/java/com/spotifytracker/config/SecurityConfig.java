package com.spotifytracker.config;

import com.spotifytracker.model.User;
import com.spotifytracker.repository.UserRepository;
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
import static //
        org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserRepository userRepository;

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
                    String username = oauth2User.getName();// Extract the username from the OAuth2User
            String displayName = (String) oauth2User.getAttributes().get("display_name");// Extract the display name from the OAuth2User

                    // Check if the user exists in your database
                    User user = userRepository.findByDisplayName(displayName);
            if (user != null) {
                // User exists, update any necessary information
                user.setDisplayName(displayName);
                // Update other user details if needed
                userRepository.save(user);
            } else {
                // User doesn't exist, create a new user
                user = new User();
                user.setDisplayName(displayName);
                // Set other user details if needed
                userRepository.save(user);
            }

            // Return the OAuth2User instance
            return oauth2User;
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