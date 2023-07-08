package com.spotifytracker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AccessTokenInterceptor implements ClientHttpRequestInterceptor {
    private final OAuth2AuthorizedClientService authorizedClientService;

    @Autowired
    public AccessTokenInterceptor(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        // Add access token to request
        OAuth2AccessToken accessToken = getAccessToken();
        HttpHeaders headers = request.getHeaders();
        headers.add("Authorization", "Bearer " + accessToken.getTokenValue());

        return execution.execute(request, body);
    }

    private OAuth2AccessToken getAccessToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthorizedClient auth2AuthorizedClient = authorizedClientService.loadAuthorizedClient("spotify", authentication.getName());
        return auth2AuthorizedClient.getAccessToken();
    }
}
