package com.spotifytracker.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class SpotifyAccessTokenProvider {
    @Value("${spring.security.oauth2.client.registration.spotify.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.spotify.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.spotify.redirect-uri}")
    private String redirectUri;

    @Value("${spring.security.oauth2.client.provider.spotify.authorization-uri}")
    private String authorizationUri;

    @Value("${spring.security.oauth2.client.provider.spotify.token-uri}")
    private String tokenUri;

    public String exchangeAuthorizationCodeForAccessToken(String authorizationCode) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Basic YzczODA0ZTUyZjc0NDIwYTk4YzgwMTIzZmM5Mjk2ZDU6ZDI1MmZiNzQxMzhmNDQ0OTk0MTllNjQ0MjgyMmE1M2Q=");

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "authorization_code");
        requestBody.add("code", authorizationCode);
        requestBody.add("redirect_uri", redirectUri);
        requestBody.add("client_id", clientId);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();

        // Send the POST request to exchange the authorization code for an access token
//        ResponseEntity<String> response = restTemplate.exchange(
//                tokenUri,
//                HttpMethod.POST,
//                requestEntity,
//                String.class
//        );
        ResponseEntity<String> responseEntity = restTemplate.exchange(tokenUri, HttpMethod.POST, requestEntity, String.class);

//        System.out.println(restTemplate.exchange(
//                tokenUri,
//                HttpMethod.POST,
//                requestEntity,
//                SpotifyAccessTokenResponse.class
//        ));
//        if (responseEntity != null) {
//            System.out.println("FOUND");
            return responseEntity.getBody();
//        } else {
//            System.out.println("NOT FOUND");
//            throw new RuntimeException("Failed to obtain access token from Spotify.");
//        }
    }

    private static class SpotifyAccessTokenResponse {
        private String accessToken;
        // Other fields such as token type, expires in, etc.

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        // Getters and setters for other fields
    }
}
