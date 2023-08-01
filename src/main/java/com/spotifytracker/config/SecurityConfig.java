package com.spotifytracker.config;

import com.spotifytracker.model.User;
import com.spotifytracker.service.BackgroundService;
import com.spotifytracker.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Executor;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    final private RepositoryService repoService;

    @Autowired
    public SecurityConfig(RepositoryService repoService) {
        this.repoService = repoService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/", "/login").permitAll();
                    auth.requestMatchers(toH2Console()).permitAll();
                    auth.anyRequest().authenticated();
                })
                .oauth2Login(oauth2 -> {
                    oauth2
                            .loginPage("/login")
                            .defaultSuccessUrl("/login/success")
                            .userInfoEndpoint()
                            .userService(customOAuth2UserService());
                })
                .logout(logout -> logout.logoutSuccessUrl("/login"))
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
            String id = oauth2User.getName();
            Map<String, Object> attributes = oauth2User.getAttributes();
            Optional<User> optional = repoService.findUserById(id);
            return optional.orElseGet(() -> repoService.registerUser(attributes));
        };
    }

    @Bean(name = "taskExecutor")
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("Async-");
        executor.initialize();
        return new DelegatingSecurityContextAsyncTaskExecutor(executor);
    }
}