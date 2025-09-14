package com.sbthbt.flightsight_back.services;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class OpenSkyApiService {
    private final WebClient webClient;
    private final String clientId = "sebhbt-api-client";
    private final String clientSecret = "q0axjHRP8FMf3GTRltURq9ChTUyhFQNK";

    public OpenSkyApiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public Mono<String> fetchFlightsFromLast30Days(long beginTimestamp, long endTimestamp) {
        return getAccessToken()
                .flatMap(token -> callOpenSkyApi(token, beginTimestamp, endTimestamp));
    }

    private Mono<String> getAccessToken() {
        return webClient.post()
                .uri("https://auth.opensky-network.org/auth/realms/opensky-network/protocol/openid-connect/token")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body(BodyInserters.fromFormData("grant_type", "client_credentials")
                        .with("client_id", clientId)
                        .with("client_secret", clientSecret))
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> (String) response.get("access_token"));
    }

    private Mono<String> callOpenSkyApi(String token, long beginTimestamp, long endTimestamp) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("opensky-network.org")
                        .path("/api/flights/all")
                        .queryParam("begin", beginTimestamp)
                        .queryParam("end", endTimestamp)
                        .build())
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(String.class);
    }
}
