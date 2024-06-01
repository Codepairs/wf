package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.user.UserCreateDto;
import org.example.dto.user.UserLoginDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
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

    public Mono<Map> login(@RequestBody UserLoginDto user, ServerWebExchange exchange) {
        return webClient.post()
                .uri("/login")
                .body(BodyInserters.fromValue(user))
                .retrieve()
                .toEntity(String.class)
                .doOnError(err -> log.error(err.getMessage()))
                .flatMap(responseEntity -> { // Используем flatMap вместо block
                    Map headersMap = new HashMap<>();
                    headersMap.put("Authorization", responseEntity.getHeaders().get("Authorization").get(0));
                    headersMap.put("UserId", responseEntity.getHeaders().get("UserId").get(0));
                    log.info(headersMap.toString());

                    exchange.getResponse().getHeaders().add("Authorization", (String) headersMap.get("Authorization"));
                    exchange.getResponse().getHeaders().add("UserId", (String) headersMap.get("UserId"));
                    exchange.getResponse().getHeaders().add("Access-Control-Allow-Headers", "*");

                    log.info("Headers added to exchange: {}", exchange.getResponse().getHeaders());
                    return Mono.just(headersMap);
                });
    }


    public Mono<Map> registerUser(@RequestBody UserCreateDto user, ServerWebExchange exchange) {
        return webClient.post()
                .uri("/register")
                .body(BodyInserters.fromValue(user))
                .retrieve()
                .toEntity(String.class)
                .doOnError(err -> log.error(err.getMessage()))
                .flatMap(responseEntity -> { // Используем flatMap вместо block
                    Map headersMap = new HashMap<>();
                    headersMap.put("Authorization", responseEntity.getHeaders().get("Authorization").get(0));
                    headersMap.put("UserId", responseEntity.getHeaders().get("UserId").get(0));

                    log.info(headersMap.toString());

                    exchange.getResponse().getHeaders().add("Authorization", (String) headersMap.get("Authorization"));
                    exchange.getResponse().getHeaders().add("UserId", (String) headersMap.get("UserId"));

                    log.info("Headers added to exchange: {}", exchange.getResponse().getHeaders());
                    return Mono.just(headersMap);
                });
    }
}