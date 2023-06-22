package com.spotifytracker.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    public String exchangeAuthorizationCodeForAccessToken(String authorizationCode) throws JsonProcessingException {

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

        ResponseEntity<String> responseEntity = restTemplate.exchange(tokenUri, HttpMethod.POST, requestEntity, String.class);
        // Tworzenie obiektu ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();

// Przetworzenie odpowiedzi na obiekt JsonNode
        JsonNode responseJson = objectMapper.readTree(responseEntity.getBody());

// Pobranie wartości access_token
        return responseJson.get("access_token").asText();
        }
}
