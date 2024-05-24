package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.user.UserCreateDto;
import org.example.dto.user.UserLoginDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final WebClient webClient;

    public String loginUser(UserLoginDto user) {
        String token = webClient.post()
                .uri("/login")
                .body(BodyInserters.fromValue(user))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        log.info("token: " + token);
        if (token != null) {
            System.out.println(token);
            webClient.get().headers(h -> h.setBearerAuth(token)).retrieve();
            return token;
        }
        return null;
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