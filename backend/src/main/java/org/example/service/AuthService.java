package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.user.UserCreateDto;
import org.example.dto.user.UserLoginDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final WebClient webClient;

    public Map<String, String> loginUser(UserLoginDto user) {
        ResponseEntity<?> response =  webClient.post()
                .uri("/login")
                .body(BodyInserters.fromValue(user))
                .retrieve() // Use retrieve() instead of exchange()
                .toEntity(String.class)
                .doOnError(err -> log.error(err.getMessage()))
                .block();
        Map<String, String> headersMap = new HashMap<>();
        headersMap.put("Authorization", response.getHeaders().get("Authorization").get(0));
        headersMap.put("UserId", response.getHeaders().get("UserId").get(0));
        log.info("Token = {}", response.getHeaders().get("Authorization").get(0));
        log.info("UserId = {}", response.getHeaders().get("UserId").get(0));
        return headersMap;
    }


    public Boolean registerUser(UserCreateDto user) {
        webClient.post()
                .uri("/register")
                .body(BodyInserters.fromValue(user))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return true;
    }


}